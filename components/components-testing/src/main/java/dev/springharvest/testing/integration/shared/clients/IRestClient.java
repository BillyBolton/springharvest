package dev.springharvest.testing.integration.shared.clients;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.Map;

/**
 * This interface defines the methods that will be used to make requests to the API.
 *
 * @author Billy Bolton
 * @version 1.0
 */
public interface IRestClient {

  /**
   * Given a request specification, return a request specification with the content type set to JSON.
   *
   * @return A RequestSpecification object
   */
  RequestSpecification givenJson();

  /**
   * > This function is used to make a GET request to the specified URI and then return a ValidatableResponse object
   *
   * @param uri The URI of the resource to be tested.
   * @return ValidatableResponse
   */
  ValidatableResponse getAndThen(String uri, Object... pathParams);

  /**
   * Given a URI, a map of query parameters, and a list of path parameters, perform a GET request and return the response.
   *
   * @param uri    The URI of the resource to be tested.
   * @param params The query parameters to be sent with the request.
   * @return A ValidatableResponse object
   */
  ValidatableResponse getAndThen(String uri, Map<String, Object> params, Object... pathParams);

  /**
   * > This function takes a URI, a body, and a list of path parameters, and returns a ValidatableResponse object
   *
   * @param uri  The URI of the resource to be tested.
   * @param body The body of the request.
   * @return A ValidatableResponse object
   */
  ValidatableResponse putAndThen(String uri, Object body, Object... pathParams);

  /**
   * Given a body, when I patch a URI, then I expect a response.
   *
   * @param uri  The URI of the resource to be patched.
   * @param body The body of the request.
   * @return A ValidatableResponse object
   */
  ValidatableResponse patchAndThen(String uri, Object body, Object... pathParams);

  /**
   * Given a URI, delete the resource at that URI and then return a ValidatableResponse object.
   *
   * @param uri The URI to send the request to.
   * @return A ValidatableResponse object
   */
  ValidatableResponse deleteAndThen(String uri, Object... pathParams);

  /**
   * Delete all the things, and then return a validatable response.
   *
   * @param uri  The URI of the resource to be deleted.
   * @param body The body of the request.
   * @return A ValidatableResponse object
   */
  ValidatableResponse deleteAllAndThen(String uri, Object body, Object... pathParams);

  /**
   * Given a URI, a body, a map of query parameters, and a list of path parameters, perform a POST request and return the response.
   *
   * @param uri    The URI of the resource to be tested.
   * @param body   The body of the request.
   * @param params Query parameters to be added to the request
   * @return A ValidatableResponse object
   */
  ValidatableResponse postAndThen(String uri, Object body, Map<String, Object> params, Object... pathParams);

  ValidatableResponse postAndThen(String uri, String name, File file);

  ValidatableResponse postAndThen(String uri, Object body, Map<String, Object> params);

  ValidatableResponse postAndThen(String uri, String name, File file, String mimeType);

  /**
   * If the body is not null, then set the body and return the response, otherwise just return the response.
   *
   * @param uri  The URI of the resource to be tested.
   * @param body The body of the request.
   * @return A ValidatableResponse object
   */
  ValidatableResponse postAndThen(String uri, Object body, Object... pathParams);

  RequestSpecification givenMultiPart();

}
