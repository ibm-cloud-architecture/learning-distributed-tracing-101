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

@RestController
public class HelloController {

    static int counter = 1;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        String response = formatGreeting(name);

        // simulate a slow request every 3 requests
        try {
            if (counter++ % 3 == 0) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
    }

    private String formatGreeting(String name) {
        String response = "Hello " + name + "!";
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

    @GetMapping("/error")
    public ResponseEntity<String> replyError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}