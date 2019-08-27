package com.example.servicea;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
import io.opentracing.tag.Tags;

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
            span.log("formmating string locally: " + name);
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
        String urlPath = "http://" + serviceName + ":8081/formatGreeting";
        try (Scope scope = tracer.buildSpan("format-greeting").startActive(true)) {
            URI uri = UriComponentsBuilder //
                    .fromHttpUrl(urlPath) //
                    .queryParam("name", name).build(Collections.emptyMap());
            scope.span().log("formmating string remotely: " + name);
            Tags.SPAN_KIND.set(scope.span(), Tags.SPAN_KIND_CLIENT);
            Tags.HTTP_METHOD.set(scope.span(), "GET");
            Tags.HTTP_URL.set(scope.span(), urlPath);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            return response.getBody();
        }

    }

    @GetMapping("/error")
    public ResponseEntity<String> replyError() {
        Span span = tracer.activeSpan();
        Tags.ERROR.set(span, true);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}