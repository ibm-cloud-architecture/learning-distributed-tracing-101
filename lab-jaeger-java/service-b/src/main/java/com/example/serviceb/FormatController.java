package com.example.serviceb;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormatController {

    @GetMapping("/formatGreeting")
    public String formatGreeting(@RequestParam String name) {
        String response = "Hello, from service-b " + name + "!";
        return response;
    }
}