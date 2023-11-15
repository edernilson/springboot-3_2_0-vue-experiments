package com.edernilson.demo.post;

import org.springframework.data.repository.ListCrudRepository;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 14/11/2023, terça-feira
 */
public interface PostRepository extends ListCrudRepository<Post, Integer> {
    long count();

    Post findByTitle(String firstPost);
}
