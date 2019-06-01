package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.app.OnlineMarketMain;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OnlineMarketMain.class)
public class UserControllerSecureIntegrationTest {

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
    public void shouldGetUsersPageWithoutLogin() throws Exception {
        mock.perform(get("/users"))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void shouldGetUsersPageWithLoginAdmin() throws Exception {
        mock.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetNewUserPageWithoutLogin() throws Exception {
        mock.perform(get("/users/new"))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void shouldGetNewUserPageWithLoginAdmin() throws Exception {
        mock.perform(get("/users/new"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetReviewPageWithoutLogin() throws Exception {
        mock.perform(get("/reviews"))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void shouldGetReviewPageWithLoginAdmin() throws Exception {
        mock.perform(get("/reviews"))
                .andExpect(status().isOk());
    }
}
