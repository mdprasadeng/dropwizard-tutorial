package com.solvevolve.dropwizard;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class ManagerConfiguration extends Configuration{

  private String name;
  private PhoneNetworkClientConfiguration phoneNetworkClientConfiguration;

  private DataSourceFactory dataSource;
  private JerseyClientConfiguration jerseyClient;

  @Data
  public static class PhoneNetworkClientConfiguration {
    private String url;
    private String networkPath = "network";
  }

}
