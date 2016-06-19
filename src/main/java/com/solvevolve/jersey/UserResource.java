package com.solvevolve.jersey;

import com.solvevolve.app.entities.User;
import com.solvevolve.app.models.UserWrapped;
import com.solvevolve.dropwizard.ManagerConfiguration;
import com.solvevolve.jpa.UserDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  private final UserDAO userDAO;
  private final Client client;
  private final ManagerConfiguration.PhoneNetworkClientConfiguration pnConfig;

  public UserResource(UserDAO userDAO, Client client,
                      ManagerConfiguration.PhoneNetworkClientConfiguration phoneNetworkClientConfiguration) {
    this.userDAO = userDAO;
    this.client = client;
    this.pnConfig = phoneNetworkClientConfiguration;
  }


  @POST
  @UnitOfWork
  public UserWrapped saveUser(UserWrapped userWrapped) {
    User user = getUser(userWrapped);
    userDAO.create(user);

    Response response = client.target(pnConfig.getUrl())
        .path(pnConfig.getNetworkPath())
        .path(userWrapped.getPhoneNumber() + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .put(Entity.json(userWrapped.getNetwork()));

    if (response.getStatus() == 200 ) {
      return userWrapped;
    } else {
     throw new RuntimeException("Failed to save network details");
    }
  }

  private User getUser(UserWrapped userWrapped) {
    User user = new User();
    user.setName(userWrapped.getName());
    user.setEmail(userWrapped.getEmail());
    user.setPhoneNumber(userWrapped.getPhoneNumber());
    return user;
  }

  @GET
  @UnitOfWork
  public UserWrapped getUser(@QueryParam("phone") String phone) {

    User user = userDAO.findByPhoneNumber(phone);

    UserWrapped userWrapped = getUserWrapped(user);

    UserWrapped.Network network = client.target(pnConfig.getUrl())
        .path(pnConfig.getNetworkPath())
        .path(phone + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get(UserWrapped.Network.class);

    userWrapped.setNetwork(network);
    return userWrapped;
  }

  private UserWrapped getUserWrapped(User user) {
    UserWrapped userWrapped = new UserWrapped();
    userWrapped.setName(user.getName());
    userWrapped.setEmail(user.getEmail());
    userWrapped.setPhoneNumber(user.getPhoneNumber());
    return userWrapped;
  }
}
