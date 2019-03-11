package org.openokr;

import org.openokr.application.web.ValidateCodeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

@ServletComponentScan
@SpringBootApplication
@ImportResource({
        "classpath:/META-INF/spring/appCtx-consumer.xml"
})
public class ApplicationOkrMobileWeb {

    private static Logger logger = LoggerFactory.getLogger(ApplicationOkrMobileWeb.class);

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new ValidateCodeServlet(), "/validateCodeServlet");
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return new EhCacheManagerFactoryBean();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationOkrMobileWeb.class, args);
        logger.info("启动完成！");
    }
}