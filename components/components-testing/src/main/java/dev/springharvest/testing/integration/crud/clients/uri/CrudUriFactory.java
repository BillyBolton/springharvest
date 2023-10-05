package dev.springharvest.testing.integration.crud.clients.uri;


import dev.springharvest.crud.rest.constants.CrudControllerUri;

public record CrudUriFactory(String domainContext) implements ICrudUriFactory {

  @Override
  public String getExistsByIdUri() {
    return buildUri(CrudControllerUri.EXISTS_BY_ID);
  }

  @Override
  public String getFindByIdUri() {
    return buildUri(CrudControllerUri.FIND_BY_ID);
  }

  @Override
  public String getFindAllUri() {
    return buildUri(CrudControllerUri.FIND_ALL);
  }

  @Override
  public String getPostUri() {
    return buildUri(CrudControllerUri.CREATE);
  }

  @Override
  public String getPostAllUri() {
    return buildUri(CrudControllerUri.CREATE_ALL);
  }

  @Override
  public String getPatchUri() {
    return buildUri(CrudControllerUri.UPDATE_BY_ID);
  }

  @Override
  public String getPatchAllUri() {
    return buildUri(CrudControllerUri.UPDATE_ALL);
  }

  @Override
  public String getDeleteByIdUri() {
    return buildUri(CrudControllerUri.DELETE_BY_ID);
  }

  @Override
  public String getDeleteAllByIdsUri() {
    return buildUri(CrudControllerUri.DELETE_ALL);
  }

  /**
   * It takes a URI and returns a URI
   *
   * @param uri The URI to be called.
   * @return A string that is the domain and the uri.
   */
  private String buildUri(String uri) {
    return String.format("%s%s", domainContext(), uri);
  }

}