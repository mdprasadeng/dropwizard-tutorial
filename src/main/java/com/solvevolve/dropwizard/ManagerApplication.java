package com.solvevolve.dropwizard;

import com.solvevolve.jersey.HelloResource;
import com.solvevolve.jersey.UserResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ManagerApplication extends Application<ManagerConfiguration> {

  @Override
  public void run(ManagerConfiguration configuration, Environment environment) throws Exception {
    System.out.println("Server is running using");
    System.out.println("default port :8080 for application");
    System.out.println("admin port :8081 for application");

    environment.jersey().register(HelloResource.class);
    environment.jersey().register(UserResource.class);
  }
}
