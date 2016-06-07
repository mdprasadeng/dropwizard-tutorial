package com.solvevolve.dropwizard;

public class Main {

  public static void main(String[] args) throws Exception {

    System.out.println("Hello world");
    System.out.println("Command line Arguments are " + String.join(",", args));

    new ManagerApplication().run(args);

  }
}
