package com.edernilson.demo.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: github.com/edernilson
 * @user: eder.nilson
 * @created: 15/11/2023, quarta-feira
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @GetMapping("/hello")
    String hello() {
        return "Hello from Spring Boot";
    }

}
