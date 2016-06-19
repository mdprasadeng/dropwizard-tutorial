package com.solvevolve.dropwizard;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.solvevolve.pnclient.PNConfiguration;

import org.glassfish.jersey.filter.LoggingFilter;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Environment;

public class ManagerModule extends AbstractModule {

  @Override
  protected void configure() {

  }

  @Provides
  private DataSourceFactory getDataSourceFactory(ManagerConfiguration managerConfiguration) {
    return managerConfiguration.getDataSource();
  }

  @Provides
  private JerseyClientConfiguration getJerseyClientConfig(ManagerConfiguration managerConfiguration) {
    return managerConfiguration.getJerseyClient();
  }

  @Provides
  private PNConfiguration getPNConfig(ManagerConfiguration managerConfiguration) {
    return managerConfiguration.getPnConfiguration();
  }

  @Provides
  @Singleton
  private Client getClient(Environment environment, ManagerConfiguration configuration) {
    Client client = new JerseyClientBuilder(environment)
        .using(configuration.getJerseyClient())
        .build("client");

    client.register(new LoggingFilter(Logger.getLogger("ClientLogger"), true));
    return client;
  }
}
