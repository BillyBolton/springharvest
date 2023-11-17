package dev.springharvest.library.domains.locations.constants;

import static dev.springharvest.crud.domains.base.rest.constants.CrudControllerUri.FIND;
import static dev.springharvest.shared.constants.ControllerUri.ControllerParameters.QUERY;

import dev.springharvest.errors.constants.ExceptionMessages;

public class LocationConstants {

  private LocationConstants() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static class Controller {

    public static final String TAG = "Locations";
    public static final String DOMAIN_CONTEXT = "/library/locations";

    public static final String FIND_BY_PROXIMITY = FIND + ControllerContexts.PROXIMITY;

    private Controller() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

  }

  /**
   * Constants used to build method contexts for the REST endpoints of the default CRUD controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  private static class ControllerContexts {

    public static final String PROXIMITY = "/proximity";
  }

  /**
   * Constants used to build arguments for the REST endpoints of the default CRUD controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  public static class ControllerParameters {

    private static final String LONGITUDE = "{longitude}";
    private static final String LATITUDE = "{latitude}";
    private static final String DISTANCE = "{distance}";
    public static final String PROXIMITY_QUERY = QUERY + "longitude=" + LONGITUDE + "&latitude=" + LATITUDE + "&distance=" + DISTANCE;

    private ControllerParameters() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }
  }

}
