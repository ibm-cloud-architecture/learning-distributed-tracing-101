package com.example.servicea;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

@Path("/")
public class HelloController {

    static int counter = 1;

    @Inject
    private Tracer tracer;

    @GET
    @Path("/sayHello/{name}")
    public String sayHello(@PathParam("name") String name, @Context HttpHeaders headers) {
        System.out.println("Headers: " + headers);
        try (Scope scope = tracer.buildSpan("say-hello-handler").startActive(true)) {
            Span span = scope.span();
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("event", name);
            fields.put("message", "this is a log message for name " + name);
            // you can also log a string instead of a map, key=event value=<stringvalue>
            // span.log("this is a log message for name " + name);
            span.log(fields);
            span.setBaggageItem("my-baggage", name);
            String response = formatGreetingRemote(name);
            span.setTag("response", response);
            return response;
        }
    }

    private String formatGreetingRemote(String name) {
        String serviceName = System.getenv("SERVICE_FORMATTER");
        if (serviceName == null) {
            serviceName = "localhost";
        }
        String urlPath = "http://" + serviceName + ":9081/formatGreeting";
        URI uri = UriBuilder //
                .fromPath(urlPath).queryParam("name", name).build();
        Client client = ClientBuilder.newClient();
        String responseStr = null;
        try {
            Response response = client.target(uri.toASCIIString()).request().get();
            responseStr = response.readEntity(String.class);
        } finally {
            client.close();
        }
        return responseStr;
    }

}
