package org.openokr.application.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.openokr.application.shiro.filter.FormAuthenticationFilter;
import org.openokr.utils.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by baozi on 2017/6/12.
 */
@Configuration
public class ShiroConfiguration {

    private final static Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);
    public final static String COOKIE_NAME = "okr.session.id";

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        //必须设置SecuritManager
        bean.setSecurityManager(manager);

        Map<String, Filter> filterMap = bean.getFilters();
        filterMap.put("authc", new FormAuthenticationFilter());


        //配置登录的url和登录成功的url,未授权页面
/*        bean.setSuccessUrl("/dailyReport");
        bean.setLoginUrl("/");
        bean.setUnauthorizedUrl("/error/403.htm");*/

        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/Login", "anon");
        filterChainDefinitionMap.put("/devLogin.json", "anon");
        filterChainDefinitionMap.put("/devLogin.json", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/public/**", "anon");

        filterChainDefinitionMap.put("/validateCodeServlet", "anon");
        filterChainDefinitionMap.put("/pkey.json", "anon");
        filterChainDefinitionMap.put("/error/*", "anon");

        filterChainDefinitionMap.put("/login.htm", "authc");
        filterChainDefinitionMap.put("/logout.htm", "logout");

        filterChainDefinitionMap.put("/**/*.htm", "user");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**/*.json", "user");//表示需要认证才可以访问


        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        log.info("################## 注入 shiro securityManager ##################");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        //注入缓存管理器
        manager.setCacheManager(ehCacheManager());
        //注入Cookie(记住我)管理器(remenberMeManager)
        manager.setRememberMeManager(rememberMeManager());
        //自定义会话
        manager.setSessionManager(sessionManager());
        return manager;
    }

    //自定义会话配置
    @Bean
    public SessionManager sessionManager() {
        log.info("################## 注入 shiro sessionManager ##################");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setGlobalSessionTimeout(3600000);        // 1 hour
        sessionManager.setSessionValidationInterval(120000);    // 2 min
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDAO() {
        CacheSessionDAO sessionDAO = new CacheSessionDAO();
        sessionDAO.setSessionIdGenerator(new IdGen());
        sessionDAO.setActiveSessionsCacheName("activeSessionsCache");
        sessionDAO.setCacheManager(ehCacheManager());
        return sessionDAO;
    }

    @Bean
    public Cookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName(COOKIE_NAME);
        return cookie;
    }

    //配置缓存
    @Bean
    public EhCacheManager ehCacheManager() {
        log.info("################## 注入 shiro ehCacheManager ##################");
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        log.info("################## 注入 shiro rememberMeManager ##################", CookieRememberMeManager.class);
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,默认30天 ,单位秒：60 * 60 * 24 * 30
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    //生命周期处理器
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}