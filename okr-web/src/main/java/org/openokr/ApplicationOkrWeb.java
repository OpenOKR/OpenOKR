package org.openokr;

import org.openokr.base.shiro.ValidateCodeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ServletComponentScan
@SpringBootApplication
@ImportResource({
        "classpath:/META-INF/spring/appCtx-consumer.xml"
})
public class ApplicationOkrWeb {

    private static Logger logger = LoggerFactory.getLogger(ApplicationOkrWeb.class);

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new ValidateCodeServlet(), "/validateCodeServlet");
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationOkrWeb.class, args);
        logger.info("启动完成！");
    }
}