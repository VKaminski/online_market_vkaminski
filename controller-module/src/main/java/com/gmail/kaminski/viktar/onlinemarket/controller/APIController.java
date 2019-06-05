package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.exception.RESTControllerException;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.RequestParamService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.ItemService;
import com.gmail.kaminski.viktar.onlinemarket.service.OrderService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleNewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticlePreviewDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ItemDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.OrderDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.PageDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserNewDTO;
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
    private OrderService orderService;

    public APIController(UserService userService,
                         ArticleService articleService,
                         ItemService itemService,
                         RequestParamService requestParamService,
                         OrderService orderService) {
        this.userService = userService;
        this.articleService = articleService;
        this.itemService = itemService;
        this.requestParamService = requestParamService;
        this.orderService = orderService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(
            @RequestBody UserNewDTO userDTO) {
        logger.debug(custom, "Request param: " + userDTO.toString());
        userService.add(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticlePreviewDTO>> getArticles(
            @RequestParam("page") String page,
            @RequestParam("amountElements") String amountElements
    ) {
        PageDTO<ArticlePreviewDTO> pageDTO = new PageDTO<>();
        pageDTO.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, 1));
        pageDTO.setAmountElementsOnPage(requestParamService.getInteger(amountElements, Integer.MAX_VALUE, 10));
        ArticlesRequestDTO articlesRequestDTO = new ArticlesRequestDTO();
        articlesRequestDTO.setTitle("");
        articlesRequestDTO.setDateStart(requestParamService.getDate("", 0l));
        articlesRequestDTO.setDateStop(requestParamService.getDate("", System.currentTimeMillis()));
        try {
            List<ArticlePreviewDTO> articles = articleService.getArticlesPage(articlesRequestDTO, pageDTO).getElements();
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request!", e);
        }
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity getArticle(
            @PathVariable Long id
    ) {
        try {
            ArticleDTO article = articleService.getById(id);
            return new ResponseEntity(article, HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Article with that id did not founded!", e);
        }
    }

    @PostMapping("/articles")
    public ResponseEntity addArticles(
            @RequestBody ArticleNewDTO articleDTO) {
        try {
            articleService.add(articleDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request!", e);
        }
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity deleteArticles(
            @PathVariable("id") Long id
    ) {
        try {
            articleService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Article not found!", e);
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getItems(
            @RequestParam String page,
            @RequestParam String amountElement
    ) {
        PageDTO<ItemDTO> itemsPage = new PageDTO<>();
        itemsPage.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, 1));
        itemsPage.setAmountElementsOnPage(requestParamService.getInteger(amountElement, 100, 10));
        try {
            itemService.getItemsPage(itemsPage);
            List<ItemDTO> items = itemsPage.getElements();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request!", e);
        }
    }

    @GetMapping("/items/{id}")
    public ResponseEntity getItem(
            @PathVariable Long id
    ) {
        try {
            ItemDTO item = itemService.getById(id);
            return new ResponseEntity(item, HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Items with that id not found!", e);
        }
    }

    @PostMapping("/items")
    public ResponseEntity addItem(
            @RequestBody ItemDTO itemDTO) {
        try {
            itemService.add(itemDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Items have mistakes or exist!", e);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(
            @PathVariable("id") Long id
    ) {
        try {
            itemService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Items with that id not found!", e);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders(
            @RequestParam String page,
            @RequestParam String amountElement) {
        PageDTO<OrderDTO> pageDTO = new PageDTO<>();
        pageDTO.setPage(requestParamService.getInteger(page, Integer.MAX_VALUE, 1));
        pageDTO.setAmountElementsOnPage(requestParamService.getInteger(amountElement, 100, 10));
        try {
            orderService.getOrdersPage(pageDTO, null);
            List<OrderDTO> orders = pageDTO.getElements();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Orders page not found", e);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity getOrder(
            @PathVariable Long id
    ) {
        try {
            OrderDTO orderDTO = orderService.getById(id);
            return new ResponseEntity(orderDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RESTControllerException("Please, correct your request! Order not found", e);
        }
    }

}
