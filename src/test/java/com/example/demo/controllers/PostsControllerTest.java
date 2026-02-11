package com.example.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.example.demo.models.Post;

import tools.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class PostsControllerTest {
    
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostsController controller;
	
	@Test
	void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}

	@Test
    void shouldAddMultiplePosts() throws Exception {
    Post post1 = new Post("First Post", "First Content");
    Post post2 = new Post("Second Post", "Second Content");
    ObjectMapper mapper = new ObjectMapper();
    
    this.mockMvc.perform(post("/addPost")
        .content(mapper.writeValueAsString(post1))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    
    this.mockMvc.perform(post("/addPost")
        .content(mapper.writeValueAsString(post2))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    
    this.mockMvc.perform(get("/viewPosts"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"title\":\"First Post\",\"content\":\"First Content\"},{\"title\":\"Second Post\",\"content\":\"Second Content\"}]"));
}

 
}