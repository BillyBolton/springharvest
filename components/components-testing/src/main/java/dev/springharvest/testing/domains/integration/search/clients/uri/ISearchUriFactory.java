package dev.springharvest.testing.domains.integration.search.clients.uri;

// This is an interface that will be used to create the URIs for the endpoints that will be used to
// test the API.

/**
 * > This is an interface that will be used to create the URIs for the endpoints that will be used to test the API.
 *
 * @author Billy Bolton
 * @version 1.0
 */
public interface ISearchUriFactory {

  String domainContext();

  /**
   * This function returns the URI for the search endpoint.
   *
   * @return The URI for the search endpoint.
   */
  String getPostSearchUri();

  /**
   * This function returns the URI for the search count endpoint.
   *
   * @return The URI for the search endpoint.
   */
  String getPostSearchCountUri();

  /**
   * This function returns the URI for the search exists endpoint.
   *
   * @return The URI for the search endpoint.
   */
  String getPostSearchExistsUri();

}