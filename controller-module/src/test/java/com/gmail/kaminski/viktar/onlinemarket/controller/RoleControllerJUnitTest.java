package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.RandomService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.impl.RandomServiceImpl;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.CommentDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleControllerJUnitTest {
    private MockMvc mockMvc;
    private String page = "1";
    private String amountElement = "10";
    private Long amountArticles = 27l;
    private String articleContent = "Sitting hearted on it without me. Polite do object at passed it is. Took sold add play may none him few. Celebrated delightful an especially increasing instrument am. Whatever throwing we on resolved entrance together graceful. Pain son rose more park way that. If as increasing contrasted entreaties be. Draw from upon here gone add one. Happiness remainder joy but earnestly for off. Took sold add play may none him few. Pain son rose more park way that. Whatever throwing we on resolved entrance together graceful. Mrs assured add private married removed believe did she. We leaf to snug on no need. Equally he minutes my hastily. Limits far yet turned highly repair parish talked six. Strictly numerous outlived kindness whatever on we no on addition. Now summer who day looked our behind moment coming. Ecstatic elegance gay but disposed. At none neat am do over will. Celebrated delightful an especially increasing instrument am. Effect if in up no depend seemed. At none neat am do over will.";
    private String commentContent = "We me rent been part what. Their saved linen downs tears son add music. Equally he minutes my hastily. Fortune day out married parties. So by colonel hearted ferrars. Draw from upon here gone add one.";
    private RandomService randomService = new RandomServiceImpl();

    @Mock
    private ArticleService articleService;
    @Mock
    private PaginatorService paginatorService;
    @Mock
    private ProfileService profileService;

    @Before
    public void init() {
        RoleController controller = new RoleController(profileService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldGetArticlesPage() throws Exception {
        Paginator paginator = getPaginator();
        List<ArticleDTO> articles = new ArrayList<>();
        when(articleService.getAmountArticles()).thenReturn(amountArticles);
        when(paginatorService.get(page, amountElement, amountArticles)).thenReturn(paginator);
        when(articleService.getArticles(0, 10)).thenReturn(articles);
        this.mockMvc.perform(get("/articles.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("articles"));
    }

    @Test
    public void checkArticlesAttributeOnArticlesPage() throws Exception {
        Paginator paginator = getPaginator();
        List<ArticleDTO> articles = new ArrayList<>();
        when(articleService.getAmountArticles()).thenReturn(amountArticles);
        when(paginatorService.get(page, amountElement, amountArticles)).thenReturn(paginator);
        when(articleService.getArticles(0, 10)).thenReturn(articles);
        this.mockMvc.perform(get("/articles.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("articles", articles));
    }

    @Test
    public void checkPaginatorAttributeOnArticlesPage() throws Exception {
        Paginator paginator = getPaginator();
        List<ArticleDTO> articles = new ArrayList<>();
        when(articleService.getAmountArticles()).thenReturn(amountArticles);
        when(paginatorService.get(page, amountElement, amountArticles)).thenReturn(paginator);
        when(articleService.getArticles(0, 10)).thenReturn(articles);
        this.mockMvc.perform(get("/articles.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("paginator", paginator));
    }

    @Test
    public void checkDateFieldOnArticlesPage() throws Exception {
        Paginator paginator = getPaginator();
        List<ArticleDTO> articles = new ArrayList<>();
        when(articleService.getAmountArticles()).thenReturn(amountArticles);
        when(paginatorService.get(page, amountElement, amountArticles)).thenReturn(paginator);
        when(articleService.getArticles(0, 10)).thenReturn(articles);
        this.mockMvc.perform(get("/articles.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("paginator", paginator));
    }

    @Test
    public void shouldGetArticlePage() throws Exception {
        Long articleId = 1l;
        ArticleDTO articleDTO = new ArticleDTO();
        when(articleService.getById(articleId)).thenReturn(articleDTO);
        this.mockMvc.perform(get("/articles/" + articleId + ".html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("article"));
    }

    @Test
    public void checkArticleAttributeOnArticlePage() throws Exception {
        Long articleId = 1l;
        ArticleDTO articleDTO = new ArticleDTO();
        when(articleService.getById(articleId)).thenReturn(articleDTO);
        this.mockMvc.perform(get("/articles/" + articleId + ".html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("article", articleDTO));
    }

    @Test
    public void shouldGetProfilePage() throws Exception {
        Long id = 1l;
        ProfileDTO profileDTO = new ProfileDTO();
        when(profileService.getById(id)).thenReturn(profileDTO);
        this.mockMvc.perform(get("/profile/" + id + ".html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("profile"));
    }

    @Test
    public void checkProfileAttributeOnProfilePage() throws Exception {
        Long id = 1l;
        ProfileDTO profileDTO = new ProfileDTO();
        when(profileService.getById(id)).thenReturn(profileDTO);
        this.mockMvc.perform(get("/profile.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("profile", profileDTO));
    }

    private Paginator getPaginator() {
        Paginator paginator = new Paginator();
        paginator.setPage(Integer.valueOf(page));
        paginator.setAmountElementOnPage(Integer.valueOf(amountElement));
        paginator.setTotalElement(amountArticles);
        return paginator;
    }

    private List<ArticleDTO> getArticles(Long amountArticles, int amountComments) {
        List<ArticleDTO> articles = new ArrayList<>();
        for (int i = 0; i < amountArticles; i++) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(Long.valueOf(i));
            articleDTO.setDate(new Date(System.currentTimeMillis() - randomService.get(1, Integer.MAX_VALUE)));
            articleDTO.setTitle("Title " + i);
            UserDTO userDTO = getUserDTO(i);
            articleDTO.setAuthor(userDTO);
            articleDTO.setContent(articleContent);
            List<CommentDTO> comments = new ArrayList<>();
            for (int j = 0; j < amountComments; j++) {
                CommentDTO comment = new CommentDTO();
                UserDTO commentAuthorDTO = getUserDTO(j * 200);
                comment.setAuthor(commentAuthorDTO);
                comment.setContent(commentContent);
                comment.setDate(new Date(System.currentTimeMillis() - randomService.get(1, Integer.MAX_VALUE)));
                comments.add(comment);
            }
            articleDTO.setComments(comments);
            articles.add(articleDTO);
        }
        return articles;
    }

    private UserDTO getUserDTO(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(Long.valueOf(id * 100));
        userDTO.setName("Name " + id * 100);
        userDTO.setSurname("Surname" + id * 100);
        return userDTO;
    }

}
