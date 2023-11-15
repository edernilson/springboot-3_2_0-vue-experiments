package com.edernilson.demo.post;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 14/11/2023, terça-feira
 */

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    List<Post> findAll() {
        return postRepository.findAll();
    }


    @GetMapping("/{id}")
    Optional<Post> findById(@PathVariable Integer id) {
        return Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Post create(@RequestBody @Valid Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    Post update(@PathVariable Integer id, @RequestBody @Valid Post post) {
        Optional<Post> existing = getExisting(id);

        Post updated = new Post(
                existing.get().id(),
                existing.get().userId(),
                post.title(),
                post.body(),
                existing.get().version()
        );

        return postRepository.save(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        Optional<Post> existing = getExisting(id);

        postRepository.deleteById(existing.get().id());
    }

    private Optional<Post> getExisting(Integer id) {
        Optional<Post> existing = postRepository.findById(id);
        if (!existing.isPresent()) {
            throw new PostNotFoundException();
        }
        return existing;
    }

}
