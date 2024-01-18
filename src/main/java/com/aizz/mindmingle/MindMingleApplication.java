package com.aizz.mindmingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MindMingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindMingleApplication.class);
    }

}
