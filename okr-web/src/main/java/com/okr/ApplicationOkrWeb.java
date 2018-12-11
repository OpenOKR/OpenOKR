package com.okr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@ServletComponentScan
@SpringBootApplication
@ImportResource({
        "classpath:/META-INF/spring/appCtx-consumer.xml"
})
public class ApplicationOkrWeb {

    private static Logger logger = LoggerFactory.getLogger(ApplicationOkrWeb.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationOkrWeb.class, args);
        logger.info("启动完成！");
    }
}