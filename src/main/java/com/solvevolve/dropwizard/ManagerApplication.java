package com.solvevolve.dropwizard;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

import com.solvevolve.app.entities.User;
import com.solvevolve.jersey.HelloResource;
import com.solvevolve.jersey.UserResource;
import com.solvevolve.pnclient.PNClientModule;

import org.hibernate.SessionFactory;

import io.dropwizard.Application;
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
  public void run(final ManagerConfiguration configuration, final Environment environment) throws Exception {
    System.out.println("Server is running using");
    System.out.println("default port :8080 for application");
    System.out.println("admin port :8081 for application");

    Injector injector = Guice.createInjector(
        new AbstractModule() {
          @Override
          protected void configure() {

          }

          @Provides
          private SessionFactory getSessionFactory() {
            return hibernate.getSessionFactory();
          }

          @Provides
          ManagerConfiguration getManagerConfig() {
            return configuration;
          }

          @Provides
          Environment getEnvironment() {
            return environment;
          }
        },
        new ManagerModule(),
        new PNClientModule()
    );



    environment.jersey().register(injector.getInstance(HelloResource.class));
    environment.jersey().register(injector.getInstance(UserResource.class));
  }
}
