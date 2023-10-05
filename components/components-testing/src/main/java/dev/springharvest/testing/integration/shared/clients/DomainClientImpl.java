package dev.springharvest.testing.integration.shared.clients;

import dev.springhavest.common.contracts.IClazzAware;
import dev.springhavest.common.models.dtos.BaseDTO;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;

public abstract class DomainClientImpl<D extends BaseDTO<K>, K extends Serializable> implements IDomainClient, IClazzAware<D> {

  protected D extractModel(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getObject("", getClazz());
  }

  protected List<D> extractModels(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getList("", getClazz());
  }

  protected Integer extractInteger(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .as(Integer.class);
  }

  protected Boolean extractBoolean(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .as(Boolean.class);
  }

}
