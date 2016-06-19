package com.solvevolve.pnclient;

import com.solvevolve.pnclient.internal.PNClientHystrixImpl;

import javax.ws.rs.client.Client;

public class PNClientProvider {

  public static PNClient getPNClient(Client client, PNConfiguration pnConfiguration) {
    return new PNClientHystrixImpl(client, pnConfiguration);
  }
}
