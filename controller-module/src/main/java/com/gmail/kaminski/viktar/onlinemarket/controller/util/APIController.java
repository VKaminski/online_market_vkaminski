package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import com.gmail.kaminski.viktar.onlinemarket.controller.CustomerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class APIController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");

    @PostMapping("/users")
    public ResponseEntity<String> addUser(
            @RequestBody     ) {

    }

    @GetMapping("/articles")
    public ResponseEntity<String> getArticles(
            @RequestBody     ) {

    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<String> getArticles(
            @RequestBody     ) {

    }

    @PostMapping("/articles")
    public ResponseEntity<String> addArticles(
            @RequestBody     ) {

    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticles(
            @RequestBody     ) {

    }


}
