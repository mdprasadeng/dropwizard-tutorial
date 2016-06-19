package com.solvevolve.pnclient.internal;

import com.solvevolve.pnclient.PNClient;
import com.solvevolve.pnclient.PNConfiguration;
import com.solvevolve.pnclient.PNNetwork;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PNClientImpl implements PNClient {

  private final Client client;
  private final PNConfiguration pnConfiguration;

  public PNClientImpl(Client client, PNConfiguration pnConfiguration) {
    this.client = client;
    this.pnConfiguration = pnConfiguration;
  }


  @Override
  public boolean saveNetwork(String phoneNumber, PNNetwork pnNetwork) {
    Response response = client.target(pnConfiguration.getUrl())
        .path(pnConfiguration.getNetworkPath())
        .path(phoneNumber + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .put(Entity.json(pnNetwork));
    return response.getStatus() == 200;
  }

  @Override
  public Optional<PNNetwork> getNetwork(String phoneNumber) {
    Response response = client.target(pnConfiguration.getUrl())
        .path(pnConfiguration.getNetworkPath())
        .path(phoneNumber + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get();

    response.bufferEntity();

    if (response.getStatus() == 200 && !response.readEntity(String.class).equals("null") ) {
      return Optional.ofNullable(response.readEntity(PNNetwork.class));
    } else {
      return Optional.empty();
    }
  }
}
