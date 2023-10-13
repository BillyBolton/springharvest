package dev.springharvest.testing.domains.integration.search.clients;

import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;

public interface ISearchClient<D extends BaseDTO<K>, K extends Serializable, B extends BaseFilterRequestDTO> {

  ValidatableResponse search(SearchRequestDTO<B> filters);

  ValidatableResponse searchCount(SearchRequestDTO<B> filters);

  ValidatableResponse searchExists(SearchRequestDTO<B> filters);

  List<D> searchAndExtract(SearchRequestDTO<B> filters);

  Boolean searchExistsAndExtract(SearchRequestDTO<B> filters);

  Integer searchCountAndExtract(SearchRequestDTO<B> filters);


}
