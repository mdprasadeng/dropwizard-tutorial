package com.solvevolve.dropwizard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solvevolve.app.entities.User;
import com.solvevolve.jersey.HelloResource;
import com.solvevolve.jersey.UserResource;
import com.solvevolve.jpa.UserDAO;
import com.solvevolve.pnclient.PNClient;
import com.solvevolve.pnclient.PNClientProvider;

import org.glassfish.jersey.filter.LoggingFilter;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ManagerApplication extends Application<ManagerConfiguration> {

  private final HibernateBundle<ManagerConfiguration>
      hibernate =
      new HibernateBundle<ManagerConfiguration>(User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ManagerConfiguration configuration) {
          return configuration.getDataSource();
        }
      };

  @Override
  public void initialize(Bootstrap<ManagerConfiguration> bootstrap) {
    super.initialize(bootstrap);
    bootstrap.addBundle(hibernate);
    bootstrap.addBundle(new MigrationsBundle<ManagerConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(ManagerConfiguration configuration) {
        return configuration.getDataSource();
      }
    });


  }

  @Override
  public void run(ManagerConfiguration configuration, Environment environment) throws Exception {
    System.out.println("Server is running using");
    System.out.println("default port :8080 for application");
    System.out.println("admin port :8081 for application");

    UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());

    environment.jersey().register(HelloResource.class);


    Client client = new JerseyClientBuilder(environment)
        .using(configuration.getJerseyClient())
        .build("client");

    client.register(new LoggingFilter(Logger.getLogger("ClientLogger"), true));

    PNClient pnClient = PNClientProvider.getPNClient(client, configuration.getPnConfiguration());

    environment.jersey().register(new UserResource(userDAO, pnClient));
  }
}
