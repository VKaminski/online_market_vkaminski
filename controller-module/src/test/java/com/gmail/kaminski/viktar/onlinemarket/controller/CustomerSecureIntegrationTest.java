package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.app.OnlineMarketMain;
import com.gmail.kaminski.viktar.onlinemarket.controller.model.CheckedUsers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OnlineMarketMain.class)
@WithMockUser(value = "customer@customer.com", password = "customer", roles = {"CUSTOMER"})
public class CustomerSecureIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mock;

    @Before
    public void setup() {
        mock = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldGetReviewsPage() throws Exception {
        mock.perform(get("/reviews"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldPostReviewsIdDeletePage() throws Exception {
        mock.perform(post("/reviews/1/delete"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldPostReviewsIdHidePage() throws Exception {
        mock.perform(post("/reviews/1/hide"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetUsersPage() throws Exception {
        mock.perform(get("/users"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldPostUsersIdChangePasswordPage() throws Exception {
        mock.perform(post("/users/1/changepassword"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldPostUsersIdChangeRolePage() throws Exception {
        mock.perform(post("/users/1/changerole"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetUsersNewPage() throws Exception {
        mock.perform(post("/users/new"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetLoginPage() throws Exception {
        mock.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetProfilePage() throws Exception {
        mock.perform(get("/profile"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetReviewsNewPage() throws Exception {
        mock.perform(get("/reviews/new"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetArticlesPage() throws Exception {
        mock.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetArticlesIdPage() throws Exception {
        mock.perform(get("/articles/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetItemsPage() throws Exception {
        mock.perform(get("/items"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetArticlesIdCommentsIdDeletePage() throws Exception {
        mock.perform(post("/articles/1/comments/1/delete"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetArticlesIdDeletePage() throws Exception {
        mock.perform(get("/articles/1/delete"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }


    @Test
    public void shouldPostArticlesIdEditPage() throws Exception {
        mock.perform(post("/articles/1/edit"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }


    @Test
    public void shouldGetItemsIdPage() throws Exception {
        mock.perform(get("/items/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetArticlesIdCopyPage() throws Exception {
        mock.perform(get("/items/1/copy"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetItemsIdDeletePage() throws Exception {
        mock.perform(get("/items/1/delete"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldGetOrdersIdPage() throws Exception {
        mock.perform(get("/orders/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldPostOrdersIdSwitchStatusPage() throws Exception {
        mock.perform(post("/orders/1/switchstatus"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/403"));
    }

}
