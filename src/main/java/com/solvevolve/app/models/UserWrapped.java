package com.solvevolve.app.models;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;

@Data
@JsonSnakeCase
public class UserWrapped {

  private String name;
  private String email;
  private String phoneNumber;
  private Network network;

  @Data
  public static class Network {
    private String provider;
    private String state;
  }
}
