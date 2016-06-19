package com.solvevolve.pnclient.internal;

import com.google.inject.Inject;

import com.solvevolve.pnclient.PNClient;
import com.solvevolve.pnclient.PNConfiguration;
import com.solvevolve.pnclient.PNNetwork;

import java.util.Optional;

import javax.ws.rs.client.Client;

public class PNClientHystrixImpl implements PNClient {

  private final Client client;
  private final PNConfiguration pnConfiguration;

  @Inject
  public PNClientHystrixImpl(Client client, PNConfiguration pnConfiguration) {
    this.client = client;
    this.pnConfiguration = pnConfiguration;
  }


  @Override
  public boolean saveNetwork(String phoneNumber, PNNetwork pnNetwork) {
    return new SavePNNetworkCommand(client, pnConfiguration, phoneNumber, pnNetwork).execute();
  }

  @Override
  public Optional<PNNetwork> getNetwork(String phoneNumber) {
    return new FetchPNNetworkCommand(client, pnConfiguration, phoneNumber).execute();
  }
}
