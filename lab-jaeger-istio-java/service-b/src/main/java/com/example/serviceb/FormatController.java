package com.example.serviceb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class FormatController {

    @Autowired
    private Tracer tracer;

    @GetMapping("/formatGreeting")
    public String formatGreeting(@RequestParam String name, @RequestHeader HttpHeaders headers) {
        System.out.println("Headers: " + headers);
        Span span = tracer.scopeManager().activeSpan();
        span.log("formatting message remotely for name " + name);
        String response = "Hello, from service-b " + name + "!";
        String myBaggage = span.getBaggageItem("my-baggage");
        span.log("this is baggage " + myBaggage);
        return response;
    }
}