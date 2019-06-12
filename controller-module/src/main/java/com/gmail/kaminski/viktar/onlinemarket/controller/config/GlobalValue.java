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
    @Value("${custom.date.format}")
    private String dateFormat;
    @Value("${custom.date.default}")
    private Long defaultDate;
    @Value("${custom.date.request.start.default}")
    private Long defaultDateRequestStart;
    @Value("${custom.date.request.stop.default}")
    private Long defaultDateRequestStop;
    @Value("${custom.page.default}")
    private Integer defaultPage;
    @Value("${custom.page.amountElements.default}")
    private Integer defaultAmountElements;
    @Value("${custom.page.amountElements.max.default}")
    private Integer defaultMaxAmountElements;
    @Value("${custom.amount.buy.default}")
    private Integer defaultAmountForBuy;
    @Value("${custom.password.min.length}")
    private Integer minLengthPassword;
    @Value("${custom.password.max.length}")
    private Integer maxLengthPassword;
    @Value("${custom.scheme.xsd.path}")
    private String xsdScheme;

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

    public String getDateFormat() {
        return dateFormat;
    }

    public Long getDefaultDate() {
        if (this.defaultDate == null) {
            return System.currentTimeMillis();
        } else {
            return defaultDate;
        }
    }

    public Long getDefaultDateRequestStart() {
        return defaultDateRequestStart;
    }

    public Long getDefaultDateRequestStop() {
        if (this.defaultDateRequestStop == null) {
            return System.currentTimeMillis();
        } else {
            return defaultDateRequestStop;
        }
    }

    public Integer getDefaultPage() {
        return defaultPage;
    }

    public Integer getDefaultAmountElements() {
        return defaultAmountElements;
    }

    public Integer getDefaultMaxAmountElements() {
        return defaultMaxAmountElements;
    }

    public Integer getDefaultAmountForBuy() {
        return defaultAmountForBuy;
    }

    public Integer getMinLengthPassword() {
        return minLengthPassword;
    }

    public Integer getMaxLengthPassword() {
        return maxLengthPassword;
    }

    public String getXsdScheme() {
        return xsdScheme;
    }
}
