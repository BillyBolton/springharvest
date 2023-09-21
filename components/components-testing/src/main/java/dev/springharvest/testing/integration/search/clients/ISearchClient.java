package dev.springharvest.testing.integration.search.clients;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;

public interface ISearchClient<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable, B extends BaseFilterRequestDTO> {

  ValidatableResponse search(SearchRequestDTO<B> filters);

  List<D> searchAndExtract(SearchRequestDTO<B> filters);

}
