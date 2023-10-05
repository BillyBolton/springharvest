package dev.springharvest.testing.integration.search.clients.uri;

import dev.springharvest.search.rest.constants.SearchControllerUri;

public record SearchUriFactory(String domainContext) implements ISearchUriFactory {

  @Override
  public String getPostSearchUri() {
    return buildUri(SearchControllerUri.SEARCH);
  }

  @Override
  public String getPostSearchCountUri() {
    return buildUri(SearchControllerUri.COUNT);
  }

  @Override
  public String getPostSearchExistsUri() {
    return buildUri(SearchControllerUri.EXISTS);
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