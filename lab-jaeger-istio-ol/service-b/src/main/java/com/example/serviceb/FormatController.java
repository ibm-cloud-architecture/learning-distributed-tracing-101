package com.example.serviceb;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

@Path("/")
public class FormatController {

    @Inject
    private Tracer tracer;

    @GET
    @Path("/formatGreeting")
    public String formatGreeting(@QueryParam("name") String name, @Context HttpHeaders headers) {
        try (Scope scope = tracer.buildSpan("format-greeting").startActive(true)) {
            Span span = scope.span();
            span.log("formatting message remotely for name " + name);
            String response = "Hello, from service-b " + name + "!";
            String myBaggage = span.getBaggageItem("my-baggage");
            span.log("this is baggage " + myBaggage);
            return response;
        }
    }

}
