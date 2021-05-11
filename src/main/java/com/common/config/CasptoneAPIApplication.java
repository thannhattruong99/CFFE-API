package com.common.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@Configuration
@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com"})
public class CasptoneAPIApplication {
    private static final Logger logger = LoggerFactory.getLogger(CasptoneAPIApplication.class);
    public static void main(String[] args) throws IOException {
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");
        SpringApplication.run(CasptoneAPIApplication.class, args);
    }

    @Configuration
    public static class Config {

        @Bean
        public MessageSource messageSource () {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("messages/msg");
            return messageSource;
        }
    }
}