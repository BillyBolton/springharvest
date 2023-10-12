package dev.springharvest.crud.domains.base.rest.constants;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.shared.constants.ControllerUri;

/**
 * Contains constants for the REST endpoints of the default CRUD controllers.
 *
 * @author Billy Bolton
 * @since 1.0
 */
public class CrudControllerUri {

  public static final String COUNT = ControllerContexts.COUNT;
  public static final String FIND = ControllerContexts.FIND;
  public static final String FIND_BY_ID = FIND + ControllerParameters.ID_QUERY;
  public static final String FIND_ALL = FIND + ControllerContexts.ALL;

  public static final String EXISTS = ControllerContexts.EXISTS;
  public static final String EXISTS_BY_ID = ControllerUri.ControllerContexts.EXISTS + ControllerParameters.ID_QUERY;

  public static final String CREATE = ControllerContexts.CREATE;
  public static final String CREATE_ALL = CREATE + ControllerContexts.ALL;

  public static final String UPDATE = ControllerContexts.UPDATE;
  public static final String UPDATE_BY_ID = UPDATE + ControllerParameters.ID_QUERY;
  public static final String UPDATE_ALL = UPDATE + ControllerContexts.ALL;

  public static final String DELETE = ControllerContexts.DELETE;
  public static final String DELETE_BY_ID = DELETE + ControllerParameters.ID_QUERY;
  public static final String DELETE_ALL = DELETE + ControllerContexts.ALL;

  private CrudControllerUri() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  /**
   * Constants used to build method contexts for the REST endpoints of the default CRUD controllers.
   *
   * @author Billy Bolton
   * @since 1.0
   */
  private static class ControllerContexts {

    public static final String COUNT = "/count";
    public static final String EXISTS = "/exists";
    public static final String FIND = "/find";
    public static final String ALL = "/all";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";

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

    private static final String ID = "/{id}";

    private static final String QUERY = "?";
    public static final String ID_QUERY = QUERY + "id={id}";
    public static final String INCLUDE_NULLS_QUERY = QUERY + "includeNulls=";

    private ControllerParameters() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }
  }
}