package dev.springharvest.search.domains.base.rest.constants;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.shared.constants.ControllerUri;

/**
 * Contains constants for the REST endpoints of the default Search controllers.
 *
 * @author Billy Bolton
 * @since 1.0
 */
public class SearchControllerUri {

  public static final String SEARCH = ControllerContexts.SEARCH;
  public static final String COUNT = ControllerContexts.COUNT;
  public static final String EXISTS = ControllerContexts.EXISTS;

  private SearchControllerUri() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  /**
   * Constants used to build method contexts for the REST endpoints of the default Search controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  private static class ControllerContexts {

    public static final String SEARCH = "/search";
    public static final String COUNT = SEARCH + "/count";
    public static final String EXISTS = SEARCH + ControllerUri.ControllerContexts.EXISTS;

    private ControllerContexts() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }
  }

  /**
   * Constants used to build arguments for the REST endpoints of the default Search controllers.
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
