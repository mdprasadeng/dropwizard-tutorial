package com.solvevolve.dropwizard;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import lombok.Data;

@Data
public class ManagerConfiguration extends Configuration{

  @Valid
  @NotNull
  private String name;


  @Valid
  @NotNull
  private JerseyClientConfiguration jerseyClient;

  @Valid
  @NotNull
  private String firebaseUrl;

}
