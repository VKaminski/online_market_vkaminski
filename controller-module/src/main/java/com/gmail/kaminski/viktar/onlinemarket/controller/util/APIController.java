package com.gmail.kaminski.viktar.onlinemarket.controller.util;

import com.gmail.kaminski.viktar.onlinemarket.controller.CustomerController;
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
    public ArticleDTO getArticle(
            @PathVariable Long id
    ) {

    }

    @PostMapping("/articles")
    public List<ArticleDTO> addArticles(
            @RequestBody) {

    }

    @DeleteMapping("/articles/{id}")
    public ArticleDTO deleteArticles(
            @PathVariable("id") Long id
    ) {

    }


}
