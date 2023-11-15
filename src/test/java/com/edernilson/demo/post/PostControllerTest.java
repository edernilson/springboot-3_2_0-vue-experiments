package com.edernilson.demo.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 14/11/2023, terça-feira
 */


@DisplayName("Post controller tests")
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostRepository postRepository;

    List<Post> posts = new ArrayList<>();

    @BeforeEach
    void setup() {
        // create some posts
        posts = List.of(
                new Post(1, 1, "First post", "This is my first post", null),
                new Post(2, 1, "Second post", "This is my second post", null)
        );
    }

    @Test
    @Description("Posts Controller Tests")
    void shouldFindAllPosts() throws Exception {
        String jsonResponse = """
                [
                  {
                    "id": 1,
                    "userId": 1,
                    "title": "First post",
                    "body": "This is my first post",
                    "version": null
                  },
                  {
                    "id": 2,
                    "userId": 1,
                    "title": "Second post",
                    "body": "This is my second post",
                    "version": null
                  }
                ]                                
                """;
        when(postRepository.findAll()).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void shouldFindPostWhenGivenValidID() throws Exception {
        when(postRepository.findById(1)).thenReturn(Optional.of(posts.get(0)));

        var post = posts.get(0);
        String jsonResponse = """
                 {
                  "id": 1,
                  "userId": 1,
                  "title": "First post",
                  "body": "This is my first post",
                  "version": null
                }
                 """;
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void shouldNotFindPostWhenGivenInvalidID() throws Exception {
        when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);

        mockMvc.perform(get("/api/posts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewPostWhenPostIsValid() throws Exception {
        Post post = new Post(3, 1, "NEW TITLE", "NEW BODY", null);
        when(postRepository.save(post)).thenReturn(post);
        String json = """
                 {
                  "id": 3,
                  "userId": 1,
                  "title": "NEW TITLE",
                  "body": "NEW BODY",
                  "version": null
                }
                 """;
        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreatePostWhenPostIsValid() throws Exception {
        Post post = new Post(3, 1, "NEW TITLE", "", null);
        when(postRepository.save(post)).thenReturn(post);
        String json = """
                 {
                  "id": 3,
                  "userId": 1,
                  "title": "NEW TITLE",
                  "body": "",
                  "version": null
                }
                 """;
        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdatePostWhenGivenValidPost() throws Exception {
        Post update = new Post(1, 1, "This a new title", "This a new body", 1);
        when(postRepository.findById(1)).thenReturn(Optional.of(update));
        when(postRepository.save(update)).thenReturn(update);
        String json = """
                 {
                  "id": 3,
                  "userId": 1,
                  "title": "NEW TITLE",
                  "body": "This a new body",
                  "version": 1
                }
                 """;

        mockMvc.perform(put("/api/posts/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeletePostWhenGivenValidID() throws Exception {
        Post update = new Post(1, 1, "This a new title", "This a new body", 1);
        when(postRepository.findById(1)).thenReturn(Optional.of(update));
        doNothing().when(postRepository).deleteById(1);
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());
        verify(postRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldNotDeletePostWhenGivenInvalidID() throws Exception {
        doNothing().when(postRepository).deleteById(1);
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNotFound());
    }

}


