package com.common.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Configuration
@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.util", "com.screenname", "com.common.dao"})
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoApplication.class, args);
    }

}