package com.gmail.kaminski.viktar.onlinemarket.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalValue {
    @Value("${custom.role.name.administrator}")
    private String administratorRoleName;
    @Value("${custom.role.name.sale}")
    private String saleRoleName;
    @Value("${custom.role.name.customer}")
    private String customerRoleName;
    @Value("${custom.role.name.api}")
    private String APIRoleName;

    public String getAdministratorRoleName() {
        return administratorRoleName;
    }

    public String getCustomerRoleName() {
        return customerRoleName;
    }

    public String getAPIRoleName() {
        return APIRoleName;
    }

    public String getSaleRoleName() {
        return saleRoleName;
    }
}
