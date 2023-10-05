package dev.springharvest.testing.integration.search.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.pages.Page;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.testing.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.integration.search.clients.ISearchClient;
import dev.springharvest.testing.integration.search.helpers.ISearchTestFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AbstractSearchIT<D extends BaseDTO<K>, K extends Serializable,
    B extends BaseFilterRequestDTO>
    implements ISearchIT {

  protected ISearchClient<D, K, B> client;

  protected ISearchTestFactory<B> modelFactory;


  protected AbstractSearchIT(AbstractSearchClientImpl<D, K, B> client, ISearchTestFactory<B> modelFactory) {
    this.client = client;
    this.modelFactory = modelFactory;
  }

  @Nested
  class ConfigTest {

    @Test
    void contextLoads() {
      assertTrue(true);
    }

  }

  @Nested
  class PostPaths {

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void canPostSearchQuery(boolean selectAll) {
      assertEquals(1, client.searchAndExtract(SearchRequestDTO.<B>builder()
                                                  .page(Page.builder().build())
                                                  .selections(modelFactory.buildValidSelections(selectAll))
                                                  .filters(Set.of(modelFactory.buildValidFilters())).build()).size());
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void canPostSearchCountQuery(boolean selectAll) {
      assertEquals(1, client.searchCountAndExtract(SearchRequestDTO.<B>builder()
                                                       .page(Page.builder().build())
                                                       .selections(modelFactory.buildValidSelections(selectAll))
                                                       .filters(Set.of(modelFactory.buildValidFilters())).build()));
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void canPostSearchExistsQuery(boolean selectAll) {
      assertEquals(Boolean.TRUE, client.searchExistsAndExtract(SearchRequestDTO.<B>builder()
                                                                   .page(Page.builder().build())
                                                                   .selections(modelFactory.buildValidSelections(selectAll))
                                                                   .filters(Set.of(modelFactory.buildValidFilters())).build()));
    }

  }

}
