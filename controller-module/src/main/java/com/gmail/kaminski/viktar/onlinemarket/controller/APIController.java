package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
<<<<<<< HEAD
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
=======
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
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
<<<<<<< HEAD
    private UserService userService;
    private ArticleService articleService;
    private ItemService itemService;

    public APIController(UserService userService, ArticleService articleService, ItemService itemService) {
        this.userService = userService;
        this.articleService = articleService;
        this.itemService = itemService;
=======
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private UserService userService;
    private ArticleService articleService;

    public APIController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(
<<<<<<< HEAD
            @RequestBody UserDTO userDTO) {
        userService.add(userDTO);
=======
            @RequestBody NewUserDTO newUserDTO) {
        userService.add(newUserDTO);
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getArticles(
<<<<<<< HEAD
            @PathVariable Integer firstElement,
=======
            @PathVariable Long firstElement,
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
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
<<<<<<< HEAD
            @RequestBody NewArticleDTO articleDTO) {
=======
            @RequestBody ArticleDTO articleDTO) {
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
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

<<<<<<< HEAD
    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getItems(
            @PathVariable Integer firstElement,
            @PathVariable Integer amountElement
    ) {
        List<ItemDTO> items = itemService.getItems(firstElement, amountElement);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity getItem(
            @PathVariable Long id
    ) {
        ItemDTO item = itemService.getById(id);
        return new ResponseEntity(item, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity addItem(
            @RequestBody ItemDTO itemDTO) {
        itemService.add(itemDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(
            @PathVariable("id") Long id
    ) {
        itemService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
=======
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f

}
