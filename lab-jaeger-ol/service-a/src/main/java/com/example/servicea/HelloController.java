package com.example.servicea;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/")
public class HelloController {

    static int counter = 1;

    @GET
    @Path("/sayHello/{name}")
    public String sayHello(@PathParam("name") String name) {
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

    @GET
    @Path("/error")
    public Response replyError() {
        return Response.serverError().build();
    }
}
