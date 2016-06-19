package com.solvevolve.pnclient.internal;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.solvevolve.pnclient.PNConfiguration;
import com.solvevolve.pnclient.PNNetwork;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FetchPNNetworkCommand extends HystrixCommand<Optional<PNNetwork>> {

  private final Client client;
  private final PNConfiguration pnConfiguration;
  private final String phoneNumber;

  public FetchPNNetworkCommand(Client client, PNConfiguration pnConfiguration, String phoneNumber) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("pn-client"))
              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                .withExecutionTimeoutInMilliseconds(5000)));
    this.client = client;
    this.pnConfiguration = pnConfiguration;
    this.phoneNumber = phoneNumber;
  }

  @Override
  protected Optional<PNNetwork> run() throws Exception {
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
