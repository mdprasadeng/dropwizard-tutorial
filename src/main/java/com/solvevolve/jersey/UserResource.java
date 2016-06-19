package com.solvevolve.jersey;

import com.solvevolve.app.entities.User;
import com.solvevolve.app.models.UserWrapped;
import com.solvevolve.jpa.UserDAO;
import com.solvevolve.pnclient.PNClient;
import com.solvevolve.pnclient.PNNetwork;

import java.util.Optional;

import javax.ws.rs.BadRequestException;
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
  private final PNClient pnClient;


  public UserResource(UserDAO userDAO, PNClient pnClient) {
    this.userDAO = userDAO;
    this.pnClient = pnClient;
  }


  @POST
  @UnitOfWork
  public UserWrapped saveUser(UserWrapped userWrapped) {
    User user = getUser(userWrapped);
    userDAO.create(user);

    boolean saveNetwork =
        pnClient.saveNetwork(userWrapped.getPhoneNumber(), getPnNetwork(userWrapped));

    if (saveNetwork) {
      return userWrapped;
    } else {
      throw new RuntimeException("Failed to save network details");
    }
  }

  private PNNetwork getPnNetwork(UserWrapped userWrapped) {
    PNNetwork pnNetwork = new PNNetwork();
    pnNetwork.setState(userWrapped.getNetwork().getState());
    pnNetwork.setProvider(userWrapped.getNetwork().getProvider());
    return pnNetwork;
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

    Optional<User> userOptional = userDAO.findByPhoneNumber(phone);
    if (!userOptional.isPresent()) {
      throw new BadRequestException("User doesn't exist");
    }
    Optional<PNNetwork> networkOptional = pnClient.getNetwork(phone);

    return getUserWrapped(userOptional.get(), networkOptional);

  }

  private UserWrapped getUserWrapped(User user, Optional<PNNetwork> networkOptional) {
    UserWrapped userWrapped = new UserWrapped();
    userWrapped.setName(user.getName());
    userWrapped.setEmail(user.getEmail());
    userWrapped.setPhoneNumber(user.getPhoneNumber());

    if (networkOptional.isPresent()) {
      UserWrapped.Network network = new UserWrapped.Network();
      network.setProvider(networkOptional.get().getProvider());
      network.setState(networkOptional.get().getState());
      userWrapped.setNetwork(network);
    }
    return userWrapped;
  }
}
