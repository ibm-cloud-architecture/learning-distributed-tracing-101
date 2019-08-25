package com.example.demo;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class HelloController {

    @Autowired
    private Tracer tracer;

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        Span span = tracer.buildSpan("say-hello").start();
        try {
            String response = "Hello, " + name + "!";
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("name", "this is a log message for name " + name);
            span.log(fields);
            span.setTag("response", response);

            return response;
        } finally {
            span.finish();
        }
    }
}