package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        String response = "Hello, " + name + "!";
        return response;
    }
}