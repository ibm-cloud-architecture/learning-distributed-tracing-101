package com.example.servicea;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name, @RequestHeader HttpHeaders headers) {
        System.out.println("Headers: " + headers);
        Span span = tracer.scopeManager().activeSpan();
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("event", name);
        fields.put("message", "this is a log message for name " + name);
        span.log(fields);
        // you can also log a string instead of a map, key=event value=<stringvalue>
        // span.log("this is a log message for name " + name);
        span.setBaggageItem("my-baggage", name);
        String response = formatGreetingRemote(name);
        span.setTag("response", response);
        return response;
    }

    private String formatGreetingRemote(String name) {
        String serviceName = System.getenv("SERVICE_FORMATTER");
        if (serviceName == null) {
            serviceName = "localhost";
        }
        String urlPath = "http://" + serviceName + ":8081/formatGreeting";
        URI uri = UriComponentsBuilder //
                .fromHttpUrl(urlPath) //
                .queryParam("name", name).build(Collections.emptyMap());
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response.getBody();
    }

}