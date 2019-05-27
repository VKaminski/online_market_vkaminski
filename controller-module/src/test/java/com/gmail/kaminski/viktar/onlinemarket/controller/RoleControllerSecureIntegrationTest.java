package com.gmail.kaminski.viktar.onlinemarket.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleControllerSecureIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Test
    public void shouldGetArticlesPageWithoutLogin() throws Exception {
        mock.perform(get("/articles"))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = {"CUSTOMER"})
    public void shouldGetArticlesPageWithLogin() throws Exception {
        mock.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"CUSTOMER"})
    public void shouldGetArticlePageWithLogin() throws Exception {
        mock.perform(get("/articles/1"))
                .andExpect(status().isOk());
    }
}
