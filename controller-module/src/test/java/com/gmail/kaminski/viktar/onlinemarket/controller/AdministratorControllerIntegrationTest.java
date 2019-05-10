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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdministratorControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    public void shouldGetLoginPage() throws Exception {
        mock.perform(get("/login"))
                .andExpect(status().isOk());
    }

}
