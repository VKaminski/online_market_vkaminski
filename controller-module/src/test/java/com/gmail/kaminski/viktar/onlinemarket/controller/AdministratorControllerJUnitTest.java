package com.gmail.kaminski.viktar.onlinemarket.controller;

import com.gmail.kaminski.viktar.onlinemarket.controller.model.Paginator;
import com.gmail.kaminski.viktar.onlinemarket.controller.util.PaginatorService;
import com.gmail.kaminski.viktar.onlinemarket.service.ReviewService;
import com.gmail.kaminski.viktar.onlinemarket.service.RoleService;
import com.gmail.kaminski.viktar.onlinemarket.service.UserService;
import com.gmail.kaminski.viktar.onlinemarket.service.model.RoleDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdministratorControllerJUnitTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private PaginatorService paginatorService;

    private List<UserDTO> users = asList(
            new UserDTO(1L, "name1", "surname1", "patronymic1", "user1@email.com", new RoleDTO()),
            new UserDTO(2L, "name2", "surname2", "patronymic2", "user2@email.com", new RoleDTO()),
            new UserDTO(3L, "name3", "surname3", "patronymic3", "user3@email.com", new RoleDTO()),
            new UserDTO(4L, "name4", "surname4", "patronymic4", "user4@email.com", new RoleDTO()),
            new UserDTO(5L, "name5", "surname5", "patronymic5", "user5@email.com", new RoleDTO()),
            new UserDTO(6L, "name6", "surname6", "patronymic6", "user6@email.com", new RoleDTO()),
            new UserDTO(7L, "name7", "surname7", "patronymic7", "user7@email.com", new RoleDTO()),
            new UserDTO(8L, "name8", "surname8", "patronymic8", "user8@email.com", new RoleDTO()),
            new UserDTO(9L, "name9", "surname9", "patronymic9", "user9@email.com", new RoleDTO()),
            new UserDTO(10L, "name10", "surname10", "patronymic10", "user10@email.com", new RoleDTO()),
            new UserDTO(11L, "name11", "surname11", "patronymic11", "user11@email.com", new RoleDTO())
    );

    private List<String> roles = asList("ROLE_ADMINISTRATOR", "ROLE_SALE", "ROLE_CUSTOMER", "ROLE_SECURE_API");
    private Paginator paginator = new Paginator(1l, 10, 2l);

    @Before
    public void init() {
        AdministratorController controller =
                new AdministratorController(userService
                        , roleService
                        , paginatorService
                        , reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldGetUsersPage() throws Exception {
        when(userService.getUsers(0l, 10)).thenReturn(users.subList(0, 10));
        when(userService.getAmountUsers()).thenReturn(Long.valueOf(users.size()));
        when(roleService.getRoleNames()).thenReturn(roles);
        this.mockMvc.perform(get("/users.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", users.subList(0, 10)))
                .andExpect(model().attribute("roles", roles))
                .andExpect(model().attributeExists("deletedList"))
                .andExpect(forwardedUrl("users"));
    }

}
