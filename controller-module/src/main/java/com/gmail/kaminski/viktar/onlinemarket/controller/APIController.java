package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
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
    private UserService userService;
    private ArticleService articleService;
    private ItemService itemService;

    public APIController(UserService userService, ArticleService articleService, ItemService itemService) {
        this.userService = userService;
        this.articleService = articleService;
        this.itemService = itemService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(
            @RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getArticles(
            @PathVariable Integer firstElement,
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
            @RequestBody NewArticleDTO articleDTO) {
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

}
