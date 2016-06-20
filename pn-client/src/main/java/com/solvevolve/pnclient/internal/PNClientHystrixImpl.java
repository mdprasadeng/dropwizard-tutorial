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
  private final SavePNNetworkCommand.SavePNNetworkFactory savePNNetworkFactory;
  private final FetchPNNetworkCommand.FetchPNNetworkFactory fetchPNNetworkFactory;

  @Inject
  public PNClientHystrixImpl(Client client, PNConfiguration pnConfiguration,
                             SavePNNetworkCommand.SavePNNetworkFactory savePNNetworkFactory,
                             FetchPNNetworkCommand.FetchPNNetworkFactory fetchPNNetworkFactory) {
    this.client = client;
    this.pnConfiguration = pnConfiguration;
    this.savePNNetworkFactory = savePNNetworkFactory;
    this.fetchPNNetworkFactory = fetchPNNetworkFactory;
  }


  @Override
  public boolean saveNetwork(String phoneNumber, PNNetwork pnNetwork) {
    return savePNNetworkFactory.getSavePNNetworkCommand(phoneNumber, pnNetwork).execute();
  }

  @Override
  public Optional<PNNetwork> getNetwork(String phoneNumber) {
    return fetchPNNetworkFactory.getFetchPNCommand(phoneNumber).execute();
  }
}
