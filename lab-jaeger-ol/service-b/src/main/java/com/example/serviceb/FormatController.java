package com.example.serviceb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public class FormatController {

    @GET
    @Path("/formatGreeting")
    public String formatGreeting(@QueryParam("name") String name) {
        String response = "Hello, from service-b " + name + "!";
        return response;
    }

}
