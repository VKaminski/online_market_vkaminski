package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.app.OnlineMarketMain;
import com.gmail.kaminski.viktar.onlinemarket.service.ArticleService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ArticleNewDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = OnlineMarketMain.class)
public class ArticleControllerIntegrationTest {
    private final String customerEmail = "customer@customer.com";
    @Value("${custom.date.format}")
    private String dateFormat;
    @Value("${custom.page.default}")
    int pageDefault;
    @Value("${custom.page.amountElements.max.default}")
    int defaultMaxAmountElementOnPage;
    @Value("${custom.page.amountElements.default}")
    int defaultAmountElementOnPage;
    ArticleNewDTO firstArticleDTO = new ArticleNewDTO();
    ArticleNewDTO secondArticleDTO = new ArticleNewDTO();


    @Autowired
    private MockMvc mock;

    @Autowired
    private ArticleService articleService;

    @Before
    public void addArticle() {
        firstArticleDTO.setUserId(1l);
        firstArticleDTO.setTitle("test1");
        firstArticleDTO.setContent("test1");
        firstArticleDTO.setDate(new Date(946677600000l));
        articleService.add(firstArticleDTO);
        secondArticleDTO.setUserId(1l);
        secondArticleDTO.setTitle("test2");
        secondArticleDTO.setContent("test2");
        secondArticleDTO.setDate(new Date(949183200000l));
        articleService.add(secondArticleDTO);
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithEmptyParam() throws Exception {
        mock.perform(get("/articles"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(2))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithTitleSearch() throws Exception {
        mock.perform(get("/articles")
                .param("searchRequest", "test1"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(1))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithAbsentTitleSearch() throws Exception {
        mock.perform(get("/articles")
                .param("searchRequest", "test3"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(0))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithDateStopSearch() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String date = simpleDateFormat.format(firstArticleDTO.getDate().getTime());
        mock.perform(get("/articles")
                .param("dateRequestStop", date))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(1))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithDateStartSearch() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String date = simpleDateFormat.format(firstArticleDTO.getDate().getTime() + 1000 * 3600);
        mock.perform(get("/articles")
                .param("dateRequestStart", date))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(1))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithDateStartStopTitleSearch() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String startDate = simpleDateFormat.format(firstArticleDTO.getDate().getTime() - 1000 * 3600);
        String stopDate = simpleDateFormat.format(firstArticleDTO.getDate().getTime() + 1000 * 3600);
        mock.perform(get("/articles")
                .param("dateRequestStart", startDate)
                .param("dateRequestStop", stopDate)
                .param("searchRequest", "1"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(1))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithPageMinusOne() throws Exception {
        mock.perform(get("/articles")
                .param("page", "-1"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(2))))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "page", is(pageDefault))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithPageZero() throws Exception {
        mock.perform(get("/articles")
                .param("page", "0"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(2))))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "page", is(pageDefault))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithPageLetter() throws Exception {
        mock.perform(get("/articles")
                .param("page", "A"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(2))))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "page", is(pageDefault))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithTwoPage() throws Exception {
        mock.perform(get("/articles")
                .param("page", "1")
                .param("amountElement", "1"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "elements", hasSize(1))))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "page", is(1))))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "amountElementsOnPage", is(1))))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "amountElements", is(2))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithOverloadMaxElementsOnPage() throws Exception {
        mock.perform(get("/articles")
                .param("page", "1")
                .param("amountElement", String.valueOf(defaultMaxAmountElementOnPage + 1)))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "amountElementsOnPage", is(defaultMaxAmountElementOnPage))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithMinusOneElementsOnPage() throws Exception {
        mock.perform(get("/articles")
                .param("page", "1")
                .param("amountElement", "-1"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "amountElementsOnPage", is(defaultAmountElementOnPage))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithLetterElementsOnPage() throws Exception {
        mock.perform(get("/articles")
                .param("page", "1")
                .param("amountElement", "A"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "amountElementsOnPage", is(defaultAmountElementOnPage))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticlesPageWithZeroElementsOnPage() throws Exception {
        mock.perform(get("/articles")
                .param("page", "1")
                .param("amountElement", "0"))
                .andExpect(model().attribute(
                        "articlesPage", hasProperty(
                                "amountElementsOnPage", is(defaultAmountElementOnPage))));
    }

    @Test
    @WithUserDetails(value = customerEmail)
    public void shouldGetArticle() throws Exception {
        mock.perform(get("/articles/1"))
                .andExpect(model().attribute(
                        "article", hasProperty(
                                "id", is(1l))))
                .andExpect(model().attribute(
                        "article", hasProperty(
                                "title", is("test1"))));
    }

}
