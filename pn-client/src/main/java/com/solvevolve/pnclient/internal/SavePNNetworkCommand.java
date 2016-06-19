package com.solvevolve.pnclient.internal;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.solvevolve.pnclient.PNConfiguration;
import com.solvevolve.pnclient.PNNetwork;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SavePNNetworkCommand extends HystrixCommand<Boolean> {

  private final Client client;
  private final PNConfiguration pnConfiguration;
  private final String phoneNumber;
  private final PNNetwork pnNetwork;

  public SavePNNetworkCommand(Client client, PNConfiguration pnConfiguration, String phoneNumber,
                              PNNetwork pnNetwork) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("pn-client"))
              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                .withExecutionTimeoutInMilliseconds(5000)));
    this.client = client;
    this.pnConfiguration = pnConfiguration;
    this.phoneNumber = phoneNumber;
    this.pnNetwork = pnNetwork;
  }

  @Override
  protected Boolean run() throws Exception {
    Response response = client.target(pnConfiguration.getUrl())
        .path(pnConfiguration.getNetworkPath())
        .path(phoneNumber + ".json")
        .request()
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .put(Entity.json(pnNetwork));

    return response.getStatus() == 200;
  }
}
