package com.solvevolve.dropwizard;

import com.solvevolve.pnclient.PNConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class ManagerConfiguration extends Configuration{

  private String name;
  private PNConfiguration pnConfiguration;

  private DataSourceFactory dataSource;
  private JerseyClientConfiguration jerseyClient;

}
