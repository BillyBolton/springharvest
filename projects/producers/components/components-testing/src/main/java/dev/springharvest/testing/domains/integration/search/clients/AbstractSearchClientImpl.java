package dev.springharvest.testing.domains.integration.search.clients;

import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.shared.contracts.IClazzAware;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.testing.domains.integration.search.clients.uri.ISearchUriFactory;
import dev.springharvest.testing.domains.integration.search.clients.uri.SearchUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.DomainClientImpl;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSearchClientImpl<D extends BaseDTO<K>, K extends Serializable, B extends BaseFilterRequestDTO>
    extends DomainClientImpl<D, K>
    implements ISearchClient<D, K, B>, IClazzAware<D> {

  @Getter
  private final Class<D> clazz;
  protected RestClientImpl clientHelper;
  protected ISearchUriFactory uriFactory;

  @Autowired(required = true)
  protected AbstractSearchClientImpl(RestClientImpl clientHelper, SearchUriFactory uriFactory, Class<D> clazz) {
    this.clientHelper = clientHelper;
    this.uriFactory = uriFactory;
    this.clazz = clazz;
  }

  @Override
  public ValidatableResponse search(SearchRequestDTO<B> filters) {
    return clientHelper.postAndThen(uriFactory.getPostSearchUri(), filters);
  }

  @Override
  public ValidatableResponse searchCount(SearchRequestDTO<B> filters) {
    return clientHelper.postAndThen(uriFactory.getPostSearchCountUri(), filters);
  }

  @Override
  public ValidatableResponse searchExists(SearchRequestDTO<B> filters) {
    return clientHelper.postAndThen(uriFactory.getPostSearchExistsUri(), filters);
  }

  @Override
  public List<D> searchAndExtract(SearchRequestDTO<B> filters) {
    return extractModels(search(filters));
  }

  @Override
  public Integer searchCountAndExtract(SearchRequestDTO<B> filters) {
    return extractInteger(searchCount(filters));
  }

  @Override
  public Boolean searchExistsAndExtract(SearchRequestDTO<B> filters) {
    return extractBoolean(searchExists(filters));
  }

}
