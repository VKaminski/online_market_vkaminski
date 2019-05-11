package com.gmail.kaminski.viktar.onlinemarket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
