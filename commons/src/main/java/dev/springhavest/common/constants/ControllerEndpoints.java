package dev.springhavest.common.constants;

/**
 * Contains constants for the REST endpoints of the default CRUD controllers.
 *
 * @author Billy Bolton
 * @since 1.0
 */
public class ControllerEndpoints {

  public static class ControllerContexts {

    public static final String EXISTS = "/exists";

  }

  /**
   * Constants used to build arguments for the REST endpoints of the default CRUD controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  public static class ControllerParameters {

    public static final String ID = "/{id}";

    public static final String QUERY = "?";
    public static final String ID_QUERY = QUERY + "id=";
    public static final String INCLUDE_NULLS_QUERY = QUERY + "includeNulls=";

    private ControllerParameters() {
    }
  }
}