package com.common.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Configuration
@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com"})
public class CasptoneAPIApplication {
    private static final Logger logger = LoggerFactory.getLogger(CasptoneAPIApplication.class);
    public static void main(String[] args) throws IOException {
//        logger.info("Info message");
//        logger.warn("Warn message");
//        logger.error("Error message");
        SpringApplication.run(CasptoneAPIApplication.class, args);
    }

}