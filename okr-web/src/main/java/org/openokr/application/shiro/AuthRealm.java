package org.openokr.application.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.openokr.application.utils.PasswordUtil;
import org.openokr.application.web.ValidateCodeServlet;
import org.openokr.ldap.ILdapUserService;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.*;
import org.openokr.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * Created by baozi on 2017/6/11.
 */
@Service
public class AuthRealm extends AuthorizingRealm {

    protected static Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    @Value("${app.multiAccountLogin}")
    private Boolean userMultiAccountLog;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILdapUserService ldapUserService;
    //认证,登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        Session session = UserUtils.getSession();

        //失败次数校验，当用户连续输错3次之后显示验证码输入框
        int failCount = 0; String key = UserUtils.USER_CACHE_LOGIN_FAIL_COUNT_KEY + CookieUtils.getCookie(Servlets.getRequest(), ShiroConfiguration.COOKIE_NAME);
        if (CacheUtils.get(UserUtils.USER_CACHE, key) != null) {
            failCount = Integer.parseInt(CacheUtils.get(UserUtils.USER_CACHE, key).toString());
        }
        //校验登录验证码
        String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
        if (failCount > 3) {
            if ((token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code.toUpperCase()))) {
                throw new AuthenticationException("msg:验证码错误,请重试.");
            }
        }

        //校验用户名密码
        //------------------- ldap验证 start ------------------------------//
        //ldap 验证成功标志
        Boolean ldapVerify = false;
        char[] password = token.getPassword();
        StringBuilder passwordStr = new StringBuilder();
        for (int i = 0; i < password.length; i++) {
            passwordStr.append(password[i]);
        }
        try {
//            String userInfo = LdapUser.findUser(token.getUsername(),passwordStr.toString());
            String userInfo = ldapUserService.userPermissionValidation(token.getUsername(), passwordStr.toString());
            ldapVerify = true;
            logger.info("通过LDAP进行用户验证-成功 用户信息：" + userInfo);
        } catch (Exception e) {
            ldapVerify = false;
            logger.warn("通过LDAP进行用户验证-验证失败 cause ：" + e.getCause());
        }
//        try {
//            String userListJsonStr = ldapUserService.getUserByFilter(token.getUsername(), passwordStr.toString(), "01");
//            UserListDataVO userListDataVO = JSON.parseObject(userListJsonStr, UserListDataVO.class);
//            List<UserVO> userVOList = userListDataVO.getUserList();
//            userService.mergeUserFromLdap(userVOList, "01");
//        } catch (Exception e) {
//            logger.warn("同步用户-失败 cause ：" + e.getCause());
//        }
        //------------------- ldap验证 end ------------------------------//
        UserVO user = userService.getByUserName(token.getUsername());
        if (user != null) {
            // 用户是否激活
            if (!user.getActive()) {
                throw new AuthenticationException("msg:用户被禁用，请联系管理员！");
            }
            byte[] salt;
            if (ldapVerify) {
                //如果ldap认证通过则将用户密码换为ldap的密码，加密方式与原系统的加密方式需要一致
                PasswordUtil.HashPassword hashPassword = PasswordUtil.encrypt(passwordStr.toString());
                user.setPassword(hashPassword.getPassword());
                salt = Encodes.decodeHex(hashPassword.getSalt());
            } else {
                //原系统的盐
                salt = Encodes.decodeHex(user.getSalt());
            }
            return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()),
                    user.getPassword(), ByteSource.Util.bytes(salt), getName());
        }
        throw new UnknownAccountException();
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        // 获取当前已登录的用户
//        if (!Global.TRUE.equals(userMultiAccountLog)) {
//            Collection<Session> sessions = UserUtils.getSessionDAO().getActiveSessions(true, principal, UserUtils.getSession());
//            if (sessions.size() > 0) {
//                // 如果是登录进来的，则踢出已在线用户
//                if (UserUtils.getSubject().isAuthenticated()) {
//                    for (Session session : sessions) {
//                        UserUtils.getSessionDAO().delete(session);
//                    }
//                }
//                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
//                else {
//                    UserUtils.getSubject().logout();
//                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
//                }
//            }
//        }
        UserVOExt userVOExt = UserUtils.getByUserName(principal.getUserName());
        if (userVOExt != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<MenuVOExt> menuVOExtList = UserUtils.getMenuList();
            List<PermissionVOExt> permissionVOExtList = UserUtils.getPermissionList();
            // 添加基于Permission的权限信息
//            menuList.stream().filter(menu -> StringUtils.isNotBlank(menu.getPermission())).forEach(menu -> {
//                // 添加基于Permission的权限信息
//                for (String permission : StringUtils.split(menu.getPermission(), ",")) {
//                    info.addStringPermission(permission);
//                }
//            });
            for (PermissionVOExt permissionVOExt : permissionVOExtList) {
                if (StringUtils.isNotBlank(permissionVOExt.getCode())) {
                    info.addStringPermission(permissionVOExt.getCode());
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (RoleVOExt roleVOExt : userVOExt.getRoleVOExtList()) {
                info.addRole(roleVOExt.getName());
            }
            // 更新登录IP和时间
//            userVOExt.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
//            userVOExt.setLoginDate(DateUtils.getCurrentDate());
//            userService.updateUserLoginInfo(userVOExt);
            // 记录登录日志
//            LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            return info;
        } else {
            return null;
        }
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserUtils.HASH_ALGORITHM);
        matcher.setHashIterations(UserUtils.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String userName; // 登录名
        private String realName; // 真实姓名
        private boolean mobileLogin; // 是否手机登录

        public Principal(UserVO user, boolean mobileLogin) {
            this.id = user.getId();
            this.userName = user.getUserName();
            this.realName = user.getRealName();
            this.mobileLogin = mobileLogin;
        }

        public String getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getRealName() {
            return realName;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try {
                return (String) UserUtils.getSession().getId();
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return id;
        }
    }
}