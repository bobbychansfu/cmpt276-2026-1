package com.example.demo.controllers;

import com.example.demo.models.Users;
import com.example.demo.models.UsersRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;


    @SuppressWarnings("null")
    @Test
    void testAddUserSaves() throws Exception {
        mockMvc.perform(post("/users/add")
                .param("name", "Alice")
                .param("email", "alice@email.com")
                .param("age", "22")
                .param("type", "regular"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/view"));

        verify(usersRepository).save(any(Users.class));
    }

    @SuppressWarnings("null")
    @Test
    void testViewAllUsersEmpty() throws Exception {
        when(usersRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/users/view"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", hasSize(0)));
    }

    @SuppressWarnings("null")
    @Test
    void testViewUsersNonempty() throws Exception {
        Users user1 = new Users("Alice", "alice@email.com", 22);
        Users user2 = new Users("Bob", "bob@email.com", 40);
        when(usersRepository.findAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users/view"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("name", is("Alice")),
                                hasProperty("email", is("alice@email.com")),
                                hasProperty("age", is(22))
                        )
                )))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("name", is("Bob")),
                                hasProperty("email", is("bob@email.com")),
                                hasProperty("age", is(40))
                        )
                )));
    }
}