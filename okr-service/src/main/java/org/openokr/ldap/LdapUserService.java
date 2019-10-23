package org.openokr.ldap;

import com.zzheng.framework.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.openokr.enumerate.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * @Desc:
 * @author: cww
 * @DateTime: 2019/10/21 14:43
 */
@Service
public class LdapUserService implements ILdapUserService {
    protected static Logger logger = LoggerFactory.getLogger(LdapUserService.class);

    private static String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    private static String LDAP_SAMACCOUNTNAME = "sAMAccountName";
    private static String LDAP_DISPLAYNAME = "displayName";
    private static String LDAP_MAIL = "mail";
    private static String LDAP_TELEPHONENUMBER = "telephoneNumber";
    private static String LDAP_DEPARTMENT = "department";

    private static String ldapUrl;
    private static String accountPrefix;
    private static String baseDn;

    /**
     * LDAP 访问地址
     */
    @Value("${app.ldap.urls}")
    private String LDAP_URL;

    /**
     * 用户名前缀
     */
    @Value("${app.ldap.account-prefix}")
    private String ACCOUNT_PREFIX;
    /**
     * 用户名前缀
     */
    @Value("${app.ldap.embedded.base-dn}")
    private String BASE_DN;

    @PostConstruct
    public void transport() {
        ldapUrl = this.LDAP_URL;
        accountPrefix = this.ACCOUNT_PREFIX;
        baseDn = this.BASE_DN;
    }

    /**
     * 验证 ldap 用户权限
     *
     * @param ldapAccount
     * @param ldapPwd
     * @return 返回用户信息 json 字符串
     * @throws BusinessException
     */
    @Override
    public String userPermissionValidation(String ldapAccount, String ldapPwd) throws BusinessException {
        // 设置搜索过滤条件
        logger.info("查询 ldap 用户列表-开始 用户：" + ldapAccount);
        String filter = "(sAMAccountName=" + ldapAccount + ")";
        try {
            return getUser(ldapAccount, ldapPwd, filter);
        } catch (NamingException e) {
            logger.error("查询 ldap 用户列表-异常 e:{},cause:{}", e.getMessage(),e.getCause());
            throw new BusinessException("查询 ldap 用户列表 异常 e:" + e.getCause());
        } catch (Exception e) {
            logger.error("查询 ldap 用户列表-失败 e:{},cause:{}", e.getMessage(),e.getCause());
            throw new BusinessException("查询 ldap 用户列表 失败 e:" + e.getCause());
        }
    }
    @Value("${spring.profiles}")
    private String profile;
    @Override
    public String getUserByFilter(String ldapAccount, String ldapPwd, String findRole) throws BusinessException {
        String filter;
        logger.info("查询 ldap 用户列表-开始 用户：" +ldapAccount + ", 查询角色userRole : " + findRole);
        try {
            filter = RoleEnum.getFilterByProfileAndRole(findRole).replace("baseDn",baseDn);
            String userListJsonStr = getUser(ldapAccount, ldapPwd, filter);
            logger.info("查询 ldap 用户列表-完成 返回 userListJsonStr ：" + userListJsonStr);
            return userListJsonStr;
        } catch (NamingException e) {
            logger.error("查询 ldap 用户列表-异常 e:{},cause:{}", e.getMessage(),e.getCause());
            throw new BusinessException("查询 ldap 用户列表 异常 e:" + e.getCause());
        } catch (Exception e) {
            logger.error("查询 ldap 用户列表-失败 e:{},cause:{}", e.getMessage(),e.getCause());
            throw new BusinessException("查询 ldap 用户列表 失败 e:" + e.getCause());
        }
    }

    private static String getUser(String account, String password, String filter) throws javax.naming.NamingException {
        LdapContext ctx = connectLDAP(account, password);
        StringBuilder strJson = new StringBuilder();
        // 定制返回属性
        String[] attrPersonArray = {LDAP_SAMACCOUNTNAME,LDAP_DISPLAYNAME,LDAP_MAIL,LDAP_DEPARTMENT,LDAP_TELEPHONENUMBER};
        SearchControls searchControls = new SearchControls();
        // 设置搜索范围
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(3000);
        searchControls.setReturningAttributes(attrPersonArray);
        NamingEnumeration<SearchResult> answer = ctx.search("", filter, searchControls);
        // 拼装成 json 便于数据转换
        strJson.append("{\"userList\": [");
        while(answer.hasMoreElements()) {
            SearchResult result = answer.next();
            Attributes attrs = result.getAttributes();
            // 读取属性值
            strJson.append("{");
            for (int i = 0; i < attrPersonArray.length; i++) {
                BasicAttribute attribute = (BasicAttribute) attrs.get(attrPersonArray[i]);
                strJson = strJson.append("\"" + attribute.getID() + "\": \"" + attribute.get() + "\"");
                if (i < attrPersonArray.length - 1) {
                    strJson.append(",");
                }
            }
            strJson.append("},");
        }
        strJson.deleteCharAt(strJson.lastIndexOf(","));
        strJson.append("]}");
        String finalUserListJson = strJson.toString().replaceAll(LDAP_SAMACCOUNTNAME,"userName")
                .replaceAll(LDAP_DISPLAYNAME,"realName")
                .replaceAll(LDAP_TELEPHONENUMBER,"phone")
                .replaceAll(LDAP_MAIL,"email")
                .replaceAll(LDAP_DEPARTMENT,"organizationName");
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                System.out.println("NamingException in close():" + e);
            }
        }
        return finalUserListJson;
    }


    private static LdapContext connectLDAP(String username, String ldapPwd) throws NamingException, javax.naming.NamingException {
        if (StringUtils.isBlank(ldapUrl)) {
            logger.warn("通过LDAP进行用户验证-LDAP 访问地址为空-验证结束");
            throw new NamingException("LDAP URL is null");
        }

        if (StringUtils.isBlank(accountPrefix)) {
            logger.warn("通过LDAP进行用户验证-LDAP 用户id前缀为空-将继续验证");
        }
//        String ldapUrl = "ldap://adsrv01.yqb.yuanqubao.com/dc=yqb,dc=yuanqubao,dc=com";  // LDAP 访问地址
        String ldapAccount = accountPrefix + "\\" + username;
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
        env.put(Context.PROVIDER_URL, ldapUrl + "/" + baseDn);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapAccount);

        env.put(Context.SECURITY_CREDENTIALS, ldapPwd);
        LdapContext ctxTDS = new InitialLdapContext(env,null);
        return ctxTDS;
    }
}
