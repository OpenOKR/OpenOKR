package org.openokr.application.ldap;

import org.openokr.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * @Desc:   连接LDAP验证用户名密码是否正确
 * @author: cww
 * @DateTime: 2019/10/15 14:46
 */
@Component
public class AuthUser {

    protected static Logger logger = LoggerFactory.getLogger(AuthUser.class);

    private static String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    private static String ldapUrl;
    private static String accountPrefix;
    /**
     * LDAP 访问地址
     */
    @Value("${spring.ldap.urls}")
    private String LDAP_URL;

    /**
     * 用户名前缀
     */
    @Value("${spring.ldap.account-prefix}")
    private String ACCOUNT_PREFIX;

    @PostConstruct
    public void transport() {
        ldapUrl = this.LDAP_URL;
        accountPrefix = this.ACCOUNT_PREFIX;
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
//        String ldapPwd = "a730ai919a+";  //密码

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapAccount);

        env.put(Context.SECURITY_CREDENTIALS, ldapPwd);
        LdapContext ctxTDS = new InitialLdapContext(env,null);
        return ctxTDS;
    }

    public static String findUser(String umAccount, String ldapPwd) throws javax.naming.NamingException {
        LdapContext ctx = connectLDAP(umAccount, ldapPwd);
        StringBuilder str = new StringBuilder();
        // 设置搜索过滤条件
        String filter = "(sAMAccountName=" + umAccount + ")";
        // 定制返回属性
        String[] attrPersonArray = {"userPrincipalName","department","mailNickname","telephoneNumber"};
        SearchControls searchControls = new SearchControls();
        // 设置搜索范围
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(3000);
        searchControls.setReturningAttributes(attrPersonArray);
        NamingEnumeration<SearchResult> answer = ctx.search("", filter, searchControls);
        while(answer.hasMoreElements()) {
            SearchResult result = answer.next();
            Attributes attrs = result.getAttributes();
            // 读取属性值
            for (int i = 0; i < attrPersonArray.length; i++) {
                str = str.append(attrs.get(attrPersonArray[i]) + ",");
            }
        }
        str.deleteCharAt(str.lastIndexOf(","));
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                System.out.println("NamingException in close():" + e);
            }
        }
        return str.toString();
    }
}
