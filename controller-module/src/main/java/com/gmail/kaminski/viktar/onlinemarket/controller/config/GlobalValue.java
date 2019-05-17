package com.gmail.kaminski.viktar.onlinemarket.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalValue {
    public static String ADMINISTRATOR_ROLE_NAME;

    @Value("${custom.role.name.administrator}")
    public static void setAdministratorRoleName(String name) {
        ADMINISTRATOR_ROLE_NAME = name;
    }
}
