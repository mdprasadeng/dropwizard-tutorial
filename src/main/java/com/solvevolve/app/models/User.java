package com.solvevolve.app.models;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;

@Data
@JsonSnakeCase
public class User {

  private String name;
  private String email;
  private String phoneNumber;
}
