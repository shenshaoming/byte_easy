package com.mbyte.easy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot启动类
 * @author  申劭明
 */
@SpringBootApplication
@EnableScheduling
public class EasyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyApplication.class, args);
    }

}
