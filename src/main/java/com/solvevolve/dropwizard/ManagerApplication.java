package com.solvevolve.dropwizard;

import com.solvevolve.jersey.HelloResource;
import com.solvevolve.jersey.UserResource;

import org.glassfish.jersey.filter.LoggingFilter;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

public class ManagerApplication extends Application<ManagerConfiguration> {

  @Override
  public void run(ManagerConfiguration configuration, Environment environment) throws Exception {
    System.out.println("Server is running using");
    System.out.println("default port :8080 for application");
    System.out.println("admin port :8081 for application");

    environment.jersey().register(HelloResource.class);


    Client client = new JerseyClientBuilder(environment)
        .using(configuration.getJerseyClient())
        .build("client");

    client.register(new LoggingFilter(Logger.getLogger("ClientLogger"), true));

    environment.jersey().register(new UserResource(client, configuration.getFirebaseUrl()));
  }
}
