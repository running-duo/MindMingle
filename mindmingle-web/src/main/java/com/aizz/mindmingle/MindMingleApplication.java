package com.aizz.mindmingle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.aizz.mindmingle")
@MapperScan("com.aizz.mindmingle.persistence.mapper")
public class MindMingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindMingleApplication.class, args);
    }

}
