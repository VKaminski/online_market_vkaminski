package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import com.gmail.kaminski.viktar.onlinemarket.controller.CustomerController;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/")
public class APIController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");

    @PostMapping("/users")
    public UserDTO addUser(
            @RequestBody UserDTO userDTO) {

    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles() {

    }

    @GetMapping("/articles/{id}")
    public ArticleDTO getArticle() {

    }

    @PostMapping("/articles")
    public List<ArticleDTO> addArticles(
            @RequestBody     ) {

    }

    @DeleteMapping("/articles/{id}")
    public ArticleDTO deleteArticles(
            @PathVariable("id") Long id
    ) {

    }


}
