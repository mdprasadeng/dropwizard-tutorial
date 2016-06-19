package com.solvevolve.jersey;

import com.solvevolve.app.entities.User;
import com.solvevolve.app.models.UserWrapped;
import com.solvevolve.jpa.UserDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  private final UserDAO userDAO;

  public UserResource(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @POST
  @UnitOfWork
  public UserWrapped saveUser(UserWrapped userWrapped) {
    User user = new User();
    user.setName(userWrapped.getName());
    user.setEmail(userWrapped.getEmail());
    user.setPhoneNumber(userWrapped.getPhoneNumber());

    userDAO.create(user);
    return userWrapped;
  }

  @GET
  @UnitOfWork
  public UserWrapped getUser(@QueryParam("phone") String phone) {

    User user = userDAO.findByPhoneNumber(phone);

    UserWrapped userWrapped = new UserWrapped();
    userWrapped.setName(user.getName());
    userWrapped.setEmail(user.getEmail());
    userWrapped.setPhoneNumber(user.getPhoneNumber());

    UserWrapped.Network network = new UserWrapped.Network();
    network.setProvider("Airtel");
    network.setState("Karnataka");
    userWrapped.setNetwork(network);
    return userWrapped;
  }
}
