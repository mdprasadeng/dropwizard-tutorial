package com.solvevolve.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ManagerApplication extends Application<ManagerConfiguration> {

  @Override
  public void run(ManagerConfiguration configuration, Environment environment) throws Exception {
    System.out.println("Servering running using");
    System.out.println("default port :8080 for application");
    System.out.println("admin port :8081 for application");
  }
}
