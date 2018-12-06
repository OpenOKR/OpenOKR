package com.okr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = {"com.okr"},
        exclude = org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration.class)
public class ApplicationOkrService {

    private static Logger logger = LoggerFactory.getLogger(ApplicationOkrService.class);

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder().sources(ApplicationOkrService.class).web(false).run(args);
        logger.info("启动完成！");
        new CountDownLatch(1).await();
    }

}