package dev.springharvest.testing.integration.search.clients;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.testing.integration.shared.clients.DomainClientImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.IUriFactory;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import dev.springhavest.common.contracts.IClazzAware;
import dev.springhavest.common.models.dtos.BaseDTO;
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
  protected IUriFactory uriFactory;

  @Autowired(required = true)
  protected AbstractSearchClientImpl(RestClientImpl clientHelper, UriFactory uriFactory, Class<D> clazz) {
    this.clientHelper = clientHelper;
    this.uriFactory = uriFactory;
    this.clazz = clazz;
  }

  @Override
  public ValidatableResponse search(SearchRequestDTO<B> filters) {
    return clientHelper.postAndThen(uriFactory.getPostSearchUri(), filters);
  }

  @Override
  public List<D> searchAndExtract(SearchRequestDTO<B> filters) {
    return extractObjects(search(filters));
  }

}
