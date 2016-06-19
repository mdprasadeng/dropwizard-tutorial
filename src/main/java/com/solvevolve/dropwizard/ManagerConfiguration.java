package com.solvevolve.dropwizard;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

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
