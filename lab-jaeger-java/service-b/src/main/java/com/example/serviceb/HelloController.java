package com.example.serviceb;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class HelloController {

    @Autowired
    private Tracer tracer;

    @GetMapping("/formatGreeting")
    public String formatGreeting(@RequestParam String name) {
        Span span = tracer.buildSpan("format-greeting").start();
        try {
            String response = "Hello, from service-b " + name + "!";
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("name", "this is a log message for name " + name);
            // check the baggage
            String myBaggage = span.getBaggageItem("my-baggage");
            fields.put("myBaggage", "this is baggage " + myBaggage);
            span.log(fields);
            span.setTag("response", response);
            return response;
        } finally {
            span.finish();
        }
    }
}