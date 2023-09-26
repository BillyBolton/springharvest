package dev.springharvest.search.rest.constants;

import dev.springharvest.errors.constants.ExceptionMessages;

/**
 * Contains constants for the REST endpoints of the default CRUD controllers.
 *
 * @author Billy Bolton
 * @since 1.0
 */
public class SearchControllerUri {

  public static final String SEARCH = ControllerContexts.SEARCH;

  private SearchControllerUri() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  /**
   * Constants used to build method contexts for the REST endpoints of the default CRUD controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  private static class ControllerContexts {

    public static final String SEARCH = "/search";

    private ControllerContexts() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }
  }

  /**
   * Constants used to build arguments for the REST endpoints of the default CRUD controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  public static class ControllerParameters {

    private ControllerParameters() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }
  }
}
