package com.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static com.util.PathConstant.*;

@Configuration
@SpringBootApplication
@RestController
@EnableAsync
@ComponentScan(basePackages = {"com"})
public class CasptoneAPIApplication {
    private static final Logger logger = LoggerFactory.getLogger(CasptoneAPIApplication.class);
    private static final String[] HTTP_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"};
    private static final String REXP_ALL = "*";

    public static void main(String[] args){
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
            messageSource.setBasename(MSG_RELATIVE_PATH);
            return messageSource;
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(REXP_ALL_PATH).allowedMethods(REXP_ALL).allowedOrigins(REXP_ALL)
                        .allowedHeaders(REXP_ALL);
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler(REXP_RESOURCE_PATH).setCachePeriod(0);
            }
        };
    }

    @Bean
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}