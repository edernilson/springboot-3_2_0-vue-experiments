package com.edernilson.demo.post;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 15/11/2023, quarta-feira
 */
@DisplayName("Post repository integration tests")
//@Category(IntegrationTest.class)
@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Tag("IntegrationTest")
public class PostRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.1");

    @Autowired
    PostRepository postRepository;

    @Test
    void connectionEstabilished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        List<Post> posts = List.of(new Post(1, 1, "First Post", "This is my first Post", null));
        postRepository.saveAll(posts);
    }

    @Test
    void shoulReturnPostByTitle() {
        Post post = postRepository.findByTitle("First Post");
        assertThat(post).isNotNull();
    }
}
