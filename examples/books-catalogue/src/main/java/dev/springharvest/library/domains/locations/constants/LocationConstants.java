package dev.springharvest.library.domains.locations.constants;

import dev.springharvest.errors.constants.ExceptionMessages;

public class LocationConstants {

  private LocationConstants() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static class Controller {

    public static final String TAG = "Locations";
    public static final String DOMAIN_CONTEXT = "/library/locations";

    private Controller() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

  }

}
