package dev.springharvest.testing.domains.integration.shared.clients;

import static io.restassured.RestAssured.given;

import groovy.util.logging.Slf4j;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.Map;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestClientImpl implements IRestClient {

  @Override
  public RequestSpecification givenJson() {
    return given().contentType(ContentType.JSON);
  }

  @Override
  public ValidatableResponse getAndThen(String uri, Object... pathParams) {
    return givenJson().when().get(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse getAndThen(String uri, Map<String, Object> params, Object... pathParams) {
    return givenJson().queryParams(params).when().get(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse putAndThen(String uri, Object body, Object... pathParams) {
    return givenJson().body(body).when().put(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse patchAndThen(String uri, Object body, Object... pathParams) {
    return givenJson().body(body).when().patch(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse deleteAndThen(String uri, Object... pathParams) {
    return givenJson().when().delete(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse deleteAllAndThen(String uri, Object body, Object... pathParams) {
    return givenJson().body(body).when().delete(uri).prettyPeek().then();
  }

  @Override
  public ValidatableResponse postAndThen(String uri, Object body, Map<String, Object> params, Object... pathParams) {
    return givenJson().queryParams(params).body(body).when().post(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse postAndThen(String uri, Object body, Object... pathParams) {
    return body != null ? givenJson().body(body).when().post(uri, pathParams).prettyPeek().then()
                        : givenJson().when().post(uri, pathParams).prettyPeek().then();
  }

  @Override
  public ValidatableResponse postAndThen(String uri, String name, File file) {
    return givenMultiPart().multiPart(name, file).when().post(uri).prettyPeek().then();
  }

  @Override
  public ValidatableResponse postAndThen(String uri, Object body, Map<String, Object> params) {
    return givenJson().queryParams(params).body(body).when().post(uri).prettyPeek().then();
  }

  @Override
  public ValidatableResponse postAndThen(String uri, String name, File file, String mimeType) {
    return givenMultiPart().multiPart(name, file, mimeType).when().post(uri).prettyPeek().then();
  }

  @Override
  public RequestSpecification givenMultiPart() {
    return given().contentType(ContentType.MULTIPART);
  }


}
