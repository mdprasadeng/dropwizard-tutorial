package com.solvevolve.pnclient;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import com.solvevolve.pnclient.internal.FetchPNNetworkCommand;
import com.solvevolve.pnclient.internal.PNClientHystrixImpl;
import com.solvevolve.pnclient.internal.SavePNNetworkCommand;

public class PNClientModule extends AbstractModule{

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(SavePNNetworkCommand.SavePNNetworkFactory.class));
    install(new FactoryModuleBuilder().build(FetchPNNetworkCommand.FetchPNNetworkFactory.class));
    bind(PNClient.class).to(PNClientHystrixImpl.class).in(Scopes.SINGLETON);
  }
}
