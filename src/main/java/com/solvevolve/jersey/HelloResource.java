package com.solvevolve.jersey;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/hello")
public class HelloResource {

  @GET
  public String get(
      @QueryParam("name") @DefaultValue("unknown") String name,
      @HeaderParam("x_client_id") @DefaultValue("client_id") String xClientId
  ) {
    return "Hello " + name + " from GET";
  }

  @POST
  public String post() {
    return "Hello from POST";
  }

}
