package dev.springharvest.testing.domains.integration.search.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.pages.Page;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.testing.domains.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.domains.integration.search.clients.ISearchClient;
import dev.springharvest.testing.domains.integration.search.factories.ISearchModelFactory;
import dev.springharvest.testing.domains.integration.shared.tests.AbstractBaseIT;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AbstractSearchIT<D extends BaseDTO<K>, K extends Serializable, B extends BaseFilterRequestDTO>
    extends AbstractBaseIT
    implements ISearchIT {

  protected ISearchClient<D, K, B> client;

  protected ISearchModelFactory<D, B> modelFactory;


  protected AbstractSearchIT(AbstractSearchClientImpl<D, K, B> client, ISearchModelFactory<D, B> modelFactory) {
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

    private static Stream<Arguments> searchQueryParameterProvider() {
      List<CriteriaOperator> criteriaOperators = List.of(CriteriaOperator.EQUALS, CriteriaOperator.IN, CriteriaOperator.NOT_EQUALS, CriteriaOperator.NOT_IN);
      List<Boolean> selectAll = List.of(Boolean.TRUE, Boolean.FALSE);
      return criteriaOperators.stream()
          .flatMap(operator -> selectAll.stream()
              .map(select -> Arguments.of(operator, select)));
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @MethodSource("searchQueryParameterProvider")
    void canPostSearchQuery(CriteriaOperator operator, boolean selectAll) {

      // assert greater than 1
      List<D> found = findAll();
      List<D> searched = client.searchAndExtract(SearchRequestDTO.<B>builder()
                                                     .page(Page.builder()
                                                               .pageNumber(1)
                                                               .pageSize(Integer.MAX_VALUE)
                                                               .build())
                                                     .selections(modelFactory.buildValidSelections(selectAll))
                                                     .filters(Set.of(modelFactory.buildValidFilters(operator, found)))
                                                     .build());
      switch (operator) {
        case IN, EQUALS:
          assertEquals(found.size(), searched.size());
          break;
        case NOT_IN, NOT_EQUALS:
          assertEquals(0, searched.size());
          break;
        default:
          throw new IllegalArgumentException("Unknown operator: " + operator);
      }
    }

    private List<D> findAll() {
      List<D> found = client.searchAndExtract(SearchRequestDTO.<B>builder()
                                                  .page(Page.builder()
                                                            .pageNumber(1)
                                                            .pageSize(Integer.MAX_VALUE)
                                                            .build())
                                                  .selections(modelFactory.buildValidSelections(true))
                                                  .build());
      assertFalse(found.isEmpty());
      return found;
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @MethodSource("searchQueryParameterProvider")
    void canPostSearchCountQuery(CriteriaOperator operator, boolean selectAll) {

      List<D> found = findAll();
      int count = client.searchCountAndExtract(SearchRequestDTO.<B>builder()
                                                   .page(Page.builder()
                                                             .pageNumber(1)
                                                             .pageSize(Integer.MAX_VALUE)
                                                             .build())
                                                   .selections(modelFactory.buildValidSelections(selectAll))
                                                   .filters(Set.of(modelFactory.buildValidFilters(operator, found)))
                                                   .build());

      switch (operator) {
        case IN, EQUALS:
          assertEquals(found.size(), count);
          break;
        case NOT_EQUALS, NOT_IN:
          assertEquals(0, count);
          break;
        default:
          throw new IllegalArgumentException("Unknown operator: " + operator);
      }

    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @MethodSource("searchQueryParameterProvider")
    void canPostSearchExistsQuery(CriteriaOperator operator, boolean selectAll) {

      List<D> found = findAll();
      boolean expectedExists = switch (operator) {
        case EQUALS, IN -> !found.isEmpty();
        case NOT_IN, NOT_EQUALS -> found.isEmpty();
        default -> throw new IllegalArgumentException("Unknown operator: " + operator);
      };

      boolean exists = client.searchExistsAndExtract(SearchRequestDTO.<B>builder()
                                                         .page(Page.builder()
                                                                   .pageNumber(1)
                                                                   .pageSize(Integer.MAX_VALUE)
                                                                   .build())
                                                         .selections(modelFactory.buildValidSelections(selectAll))
                                                         .filters(Set.of(modelFactory.buildValidFilters(operator, found))).build());
      assertEquals(expectedExists, exists);
    }

  }

}
