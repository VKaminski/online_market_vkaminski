package com.gmail.kaminski.viktar.onlinemarket.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalValue {
    @Value("${custom.role.name.administrator}")
    private String administratorRoleName;

    public String getAdministratorRoleName() {
        return administratorRoleName;
    }
}
