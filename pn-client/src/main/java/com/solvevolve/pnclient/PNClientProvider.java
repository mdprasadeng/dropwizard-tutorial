package com.solvevolve.pnclient;

import com.solvevolve.pnclient.internal.PNClientImpl;

import javax.ws.rs.client.Client;

public class PNClientProvider {

  public static PNClient getPNClient(Client client, PNConfiguration pnConfiguration) {
    return new PNClientImpl(client, pnConfiguration);
  }
}
