package dev.springharvest.testing.domains.integration.shared.clients.uri;

import dev.springharvest.errors.constants.ExceptionMessages;

public class UriUtils {

  private UriUtils() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  /**
   * It takes a URI and returns a URI
   *
   * @param uri The URI to be called.
   * @return A string that is the domain and the uri.
   */
  public static String buildUri(String domainContext, String uri) {
    return String.format("%s%s", domainContext, uri);
  }

}
