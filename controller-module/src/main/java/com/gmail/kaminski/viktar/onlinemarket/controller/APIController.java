package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.NewArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class APIController {
    private static final Logger logger = LoggerFactory.getLogger(APIController.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private UserService userService;
    private ArticleService articleService;
    private ItemService itemService;
    private RequestParamService requestParamService;

    public APIController(UserService userService, ArticleService articleService, ItemService itemService, RequestParamService requestParamService) {
        this.userService = userService;
        this.articleService = articleService;
        this.itemService = itemService;
        this.requestParamService = requestParamService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(
            @RequestBody UserDTO userDTO) {
        logger.debug(custom, "Request param: " + userDTO.toString());
        userService.add(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getArticles(
            @RequestParam("page") String page,
            @RequestParam("amountElements") String amountElements
    ) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        pageDTO.setPage(requestParamService.getElements(page, Integer.MAX_VALUE, 1));
        pageDTO.setAmountElementsOnPage(requestParamService.getElements(amountElements, Integer.MAX_VALUE, 10));
        ArticlesRequestDTO articlesRequestDTO = new ArticlesRequestDTO();
        articlesRequestDTO.setTitle("");
        articlesRequestDTO.setDateStart(requestParamService.getDate("", 0l));
        articlesRequestDTO.setDateStop(requestParamService.getDate("", System.currentTimeMillis()));
        List<ArticleDTO> articles = articleService.getArticlesPage(articlesRequestDTO, pageDTO).getElements();
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
            @RequestParam String page,
            @RequestParam String amountElement
    ) {
        PageDTO<ItemDTO> itemsPage = new PageDTO<>();
        itemsPage.setPage(requestParamService.getElements(page, Integer.MAX_VALUE, 1));
        itemsPage.setAmountElementsOnPage(requestParamService.getElements(amountElement, 100, 10));
        itemService.getItemsPage(itemsPage);
        List<ItemDTO> items = itemsPage.getElements();
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
