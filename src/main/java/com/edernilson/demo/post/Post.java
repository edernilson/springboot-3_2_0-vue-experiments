package com.edernilson.demo.post;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 14/11/2023, terça-feira
 */
public record Post(
        @Id
        Integer id,
        Integer userId,
        @NotEmpty
        String title,
        @NotEmpty
        String body,
        @Version
        Integer version
) {
}
