package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.HttpStatus;
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
    private UserService userService;
    private ArticleService articleService;

    public APIController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(
            @RequestBody NewUserDTO newUserDTO) {
        userService.add(newUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getArticles(
            @PathVariable Long firstElement,
            @PathVariable Integer amountElement
    ) {
        List<ArticleDTO> articles = articleService.getArticles(firstElement, amountElement);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity getArticle(
            @PathVariable Long id
    ) {
        ArticleDTO article = articleService.getById(id);
        return new ResponseEntity(article, HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity addArticles(
            @RequestBody ArticleDTO articleDTO) {
        articleService.add(articleDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity deleteArticles(
            @PathVariable("id") Long id
    ) {
        articleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
