package com.edernilson.demo.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 14/11/2023, terça-feira
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
}
