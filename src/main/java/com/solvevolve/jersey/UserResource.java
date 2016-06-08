package com.solvevolve.jersey;

import com.solvevolve.app.models.User;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private final Client client;
  private final String firebaseHost;

  public static final String PHONE = "phone";

  public UserResource(Client client, String firebaseHost) {
    this.client = client;
    this.firebaseHost = firebaseHost;
  }

  @GET
  public User getUser(@QueryParam(PHONE) String phone) {
    if (phone == null || phone.length() == 0) {
      throw new BadRequestException("Invalid query param " + PHONE);
    }

    //Using Client

    User user = client.target(firebaseHost)
        .path("users")
        .path(phone + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get(User.class);

    return user;
  }
}
