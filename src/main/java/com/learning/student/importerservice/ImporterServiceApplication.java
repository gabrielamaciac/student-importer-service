package com.learning.student.importerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableIntegration
@EnableScheduling
public class ImporterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImporterServiceApplication.class, args);
    }
}
