package com.solvevolve.jersey;

import com.solvevolve.app.models.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  @GET
  public User getUser(@QueryParam("email") String email) {
    User user = new User();
    user.setName("Test");
    user.setEmail("test@test.com");
    user.setPhoneNumber("999999999999");
    return user;
  }
}
