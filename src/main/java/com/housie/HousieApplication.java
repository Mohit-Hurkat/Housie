package com.housie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class HousieApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousieApplication.class, args);
    }

}
