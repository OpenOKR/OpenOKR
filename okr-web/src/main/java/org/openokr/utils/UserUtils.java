package org.openokr.utils;

import com.zzheng.framework.spring.context.utils.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.openokr.application.shiro.AuthRealm;
import org.openokr.sys.service.IMenuService;
import org.openokr.sys.service.IPermissionService;
import org.openokr.sys.service.IRoleService;
import org.openokr.sys.service.IUserService;
import org.openokr.sys.vo.MenuVOExt;
import org.openokr.sys.vo.PermissionVOExt;
import org.openokr.sys.vo.RoleVOExt;
import org.openokr.sys.vo.UserVOExt;

import java.util.List;

/**
 * Created by baozi on 2017/6/11.
 */
public class UserUtils {

    private static IUserService userService = SpringContextUtil.getBean(IUserService.class);
    private static IRoleService roleService = SpringContextUtil.getBean(IRoleService.class);
    private static IMenuService menuService = SpringContextUtil.getBean(IMenuService.class);
    private static IPermissionService permissionService = SpringContextUtil.getBean(IPermissionService.class);

    public static final String USER_CACHE = "userCache";
    private static final String USER_CACHE_ID_ = "id_";
    private static final String USER_CACHE_LOGIN_NAME_ = "ln_";
    private static final String USER_CACHE_MENU_LIST_ = "menuList_";
    private static final String USER_CACHE_ROLE_LIST_ = "roleList_";
    private static final String USER_CACHE_PERMISSION_LIST_ = "permissionList_";
    public static final String USER_CACHE_LOGIN_FAIL_COUNT_KEY = "loginFailCount_";

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    /**
     * 获取当前用户
     *
     * @return
     */
    public static UserVOExt getUser() {
        AuthRealm.Principal principal = getPrincipal();
        if (principal != null) {
            UserVOExt userVOExt = get(principal.getId());
            if (userVOExt != null) {
                return userVOExt;
            }
            return new UserVOExt();
        }
        // 如果没有登录,则返回实例空的User对象
        return new UserVOExt();
    }

    /**
     * 获取当前登录者对象
     *
     * @return
     */
    public static AuthRealm.Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            AuthRealm.Principal principal = (AuthRealm.Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static UserVOExt get(String id) {
        UserVOExt userVOExt = (UserVOExt) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (userVOExt == null) {
            userVOExt = userService.getById(id);
            if (userVOExt == null) {
                return null;
            }
            userVOExt.setRoleVOExtList(roleService.findByUserId(userVOExt.getId(), true));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + userVOExt.getId(), userVOExt);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + userVOExt.getUserName(), userVOExt);
        }
        return userVOExt;
    }

    /**
     * 根据登录名获取用户
     * @param userName
     * @return 取不到返回null
     */
    public static UserVOExt getByUserName(String userName){
        UserVOExt userVOExt = (UserVOExt) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + userName);
        if (userVOExt == null){
            userVOExt = userService.getByUserName(userName);
            if (userVOExt == null) {
                return null;
            }
            userVOExt.setRoleVOExtList(roleService.findByUserId(userVOExt.getId(), true));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + userVOExt.getId(), userVOExt);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + userVOExt.getUserName(), userVOExt);
        }
        return userVOExt;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前用户授权菜单Tree
     *
     * @return
     */
    public static List<MenuVOExt> getMenuList() {
        UserVOExt userVOExt = getUser();
        List<MenuVOExt> menuVOExtList = (List<MenuVOExt>) CacheUtils.get(USER_CACHE, USER_CACHE_MENU_LIST_ + userVOExt.getUserName());
        if (menuVOExtList == null) {
            menuVOExtList = menuService.findByUserId(userVOExt.getId());
            CacheUtils.put(USER_CACHE, USER_CACHE_MENU_LIST_ + userVOExt.getUserName(), menuVOExtList);
        }
        return menuVOExtList;
    }

    public static List<PermissionVOExt> getPermissionList() {
        UserVOExt userVOExt = getUser();
        List<PermissionVOExt> permissionVOExtList = (List<PermissionVOExt>) CacheUtils.get(USER_CACHE, USER_CACHE_PERMISSION_LIST_ + userVOExt.getUserName());
        if (permissionVOExtList == null) {
            permissionVOExtList = permissionService.findByUserId(userVOExt.getId());
            CacheUtils.put(USER_CACHE, USER_CACHE_PERMISSION_LIST_ + userVOExt.getUserName(), permissionVOExtList);
        }
        return permissionVOExtList;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
//			subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
    }

    /**
     * 清除指定用户缓存
     * @param user
     */
    public static void clearCache(UserVOExt user){
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getUserName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_MENU_LIST_ + user.getUserName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_ROLE_LIST_ + user.getUserName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_PERMISSION_LIST_ + user.getUserName());
    }

    /**
     * 获取当前用户角色列表 所有角色列表
     * @return
     */
    public static List<RoleVOExt> getRoleList(UserVOExt userVOExt){
        if (userVOExt == null) {
            return roleService.findByUserId(null, false);
        }
        List<RoleVOExt> roleList = (List<RoleVOExt>)CacheUtils.get(USER_CACHE, USER_CACHE_ROLE_LIST_ + userVOExt.getUserName());
        if (roleList == null){
            roleList = roleService.findByUserId(userVOExt.getId(), true);
            CacheUtils.put(USER_CACHE, USER_CACHE_MENU_LIST_ + userVOExt.getUserName(), roleList);
        }
        return roleList;
    }
}