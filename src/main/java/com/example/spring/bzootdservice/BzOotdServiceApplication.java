package com.example.spring.bzootdservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BzOotdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BzOotdServiceApplication.class, args);
    }

}
