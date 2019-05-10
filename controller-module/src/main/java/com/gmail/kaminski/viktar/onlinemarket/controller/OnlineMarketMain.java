package com.gmail.kaminski.viktar.onlinemarket.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.gmail.kaminski.viktar.onlinemarket"})
public class OnlineMarketMain {
    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketMain.class, args);
    }
}
