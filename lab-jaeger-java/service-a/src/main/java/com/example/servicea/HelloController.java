package com.example.servicea;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class HelloController {

    @Autowired
    private Tracer tracer;

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        Span span = tracer.buildSpan("say-hello-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span, false)) {
            span.setBaggageItem("my-baggage", name);
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("name", "this is a log message for name " + name);
            span.log(fields);
            // String response = formatGreeting(name, span);
            String response = formatGreetingRemote(name);
            span.setTag("response", response);
            return response;
        } finally {
            span.finish();
        }
    }

    private String formatGreeting(String name, Span parent) {
        Span span = tracer.buildSpan("format-greeting").asChildOf(parent).start();
        try {
            String response = "Hello, " + name + "!";
            return response;
        } finally {
            span.finish();
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    private String formatGreetingRemote(String name) {
        String serviceName = System.getenv("SERVICE_FORMATTER");
        if (serviceName == null) {
            serviceName = "localhost";
        }
        URI uri = UriComponentsBuilder //
                .fromHttpUrl("http://" + serviceName + ":8081/formatGreeting") //
                .queryParam("name", name).build(Collections.emptyMap());
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response.getBody();
    }
}