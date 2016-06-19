package com.solvevolve.pnclient;

import java.util.Optional;

public interface PNClient {

  boolean saveNetwork(String phoneNumber, PNNetwork pnNetwork);

  Optional<PNNetwork> getNetwork(String phoneNumber);

}
