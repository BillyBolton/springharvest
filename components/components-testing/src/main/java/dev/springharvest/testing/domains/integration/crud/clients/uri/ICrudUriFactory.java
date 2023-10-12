package dev.springharvest.testing.domains.integration.crud.clients.uri;

// This is an interface that will be used to create the URIs for the endpoints that will be used to
// test the API.

import dev.springharvest.testing.domains.integration.shared.clients.uri.UriFactory;

/**
 * > This is an interface that will be used to create the URIs for the endpoints that will be used to test the API.
 *
 * @author Billy Bolton
 * @version 1.0
 */
public interface ICrudUriFactory extends UriFactory {

  /**
   * > Returns the URI for the `existsById` endpoint
   *
   * @return The URI for the existsById endpoint.
   */
  String getExistsByIdUri();

  /**
   * > This function returns the URI for the findById endpoint
   *
   * @return The URI for the findById endpoint.
   */
  String getFindByIdUri();

  /**
   * > This function returns the URI for the findAll endpoint
   *
   * @return The URI for the findAll endpoint.
   */
  String getFindAllUri();

  /**
   * > This function returns the URI for the POST request
   *
   * @return The URI for the controller endpoint that will handle the POST request.
   */
  String getPostUri();

  /**
   * > This function returns the URI for the endpoint that will be used to create multiple entities at once
   *
   * @return The URI for the endpoint that will be used to create all of the entities.
   */
  String getPostAllUri();

  /**
   * > This function returns the URI for the PATCH endpoint
   *
   * @return The URI for the PATCH method.
   */
  String getPatchUri();

  /**
   * > This function returns the URI for the `PATCH` request to update all the entities in the database
   *
   * @return The URI for the patchAll method.
   */
  String getPatchAllUri();

  /**
   * > This function returns the URI for the deleteById endpoint
   *
   * @return The URI for the deleteById method.
   */
  String getDeleteByIdUri();

  /**
   * > This function returns the URI for the delete all by ids endpoint
   *
   * @return The URI for the delete all endpoint.
   */
  String getDeleteAllByIdsUri();

}