package dev.springharvest.testing.domains.integration.search.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
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
      List<CriteriaOperator> criteriaOperatorProviders = List.of(CriteriaOperator.EQUALS,
                                                                 CriteriaOperator.IN,
                                                                 CriteriaOperator.NOT_EQUALS,
                                                                 CriteriaOperator.NOT_IN);
      List<Boolean> selectAllProviders = List.of(Boolean.TRUE, Boolean.FALSE);
      List<Boolean> explodeRequestProviders = List.of(Boolean.TRUE, Boolean.FALSE);
      return criteriaOperatorProviders.stream()
          .flatMap(operator -> selectAllProviders.stream()
              .flatMap(select -> explodeRequestProviders.stream()
                  .map(explodedRequest -> Arguments.of(operator, select, explodedRequest))));
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @MethodSource("searchQueryParameterProvider")
    void canPostSearchQuery(CriteriaOperator operator, boolean selectAll, boolean explodedRequest) {

      List<D> all = findAll();
      Map<K, D> allMap = all.stream().map(model -> Map.entry(model.getId(), model)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      int allSize = allMap.size();
      Map<K, D> firstOfAllMap = all.subList(0, 1).stream().map(model -> Map.entry(model.getId(), model)).collect(Collectors.toMap(Map.Entry::getKey,
                                                                                                                                  Map.Entry::getValue));
      List<SelectionDTO> selections = modelFactory.buildValidSelections(selectAll);
      Set<B> filters = modelFactory.buildValidUniqueFilters(operator, all, explodedRequest);
      List<D> searched = client.searchAndExtract(SearchRequestDTO.<B>builder()
                                                     .page(Page.builder()
                                                               .pageNumber(1)
                                                               .pageSize(Integer.MAX_VALUE)
                                                               .build())
                                                     .selections(selections)
                                                     .filters(filters)
                                                     .build());
      Set<K> idsFromSearch = searched.stream().map(BaseDTO::getId).collect(Collectors.toSet());
      assertEquals(searched.size(), idsFromSearch.size(), "The search results contain duplicate ids.");
      SoftAssertions softly = new SoftAssertions();
      switch (operator) {
        case EQUALS:
          softly.assertThat(idsFromSearch.size()).isEqualTo(explodedRequest ? allSize : firstOfAllMap.size());
          softly.assertThat(idsFromSearch).containsAll(explodedRequest ? allMap.keySet() : firstOfAllMap.keySet());
          break;
        case NOT_EQUALS:
          softly.assertThat(idsFromSearch.size()).isEqualTo(explodedRequest && allSize > 1 ? allSize : allSize - 1);
          if (explodedRequest && allSize > 1) {
            softly.assertThat(idsFromSearch).containsAll(allMap.keySet());
          } else {
            softly.assertThat(idsFromSearch).doesNotContainAnyElementsOf(firstOfAllMap.keySet());
          }
          break;
        case IN:
          softly.assertThat(idsFromSearch.size()).isEqualTo(all.size());
          softly.assertThat(idsFromSearch).containsAll(allMap.keySet());
          break;
        case NOT_IN:
          softly.assertThat(idsFromSearch.size()).isEqualTo(explodedRequest && allSize > 1 ? allSize : 0);
          if (explodedRequest && allSize > 1) {
            softly.assertThat(idsFromSearch).containsAll(allMap.keySet());
          } else {
            softly.assertThat(idsFromSearch).doesNotContainAnyElementsOf(firstOfAllMap.keySet());
          }
          break;
        default:
          throw new IllegalArgumentException("Unknown operator: " + operator);
      }
      softly.assertAll();
    }

    private List<D> findAll() {
      List<D> all = client.searchAndExtract(SearchRequestDTO.<B>builder()
                                                .page(Page.builder()
                                                          .pageNumber(1)
                                                          .pageSize(Integer.MAX_VALUE)
                                                          .build())
                                                .selections(modelFactory.buildValidSelections(true))
                                                .build());
      assertFalse(all.isEmpty(), "No test data exists for entity with the id path: " + modelFactory.getIdPath());
      return all;
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @MethodSource("searchQueryParameterProvider")
    void canPostSearchCountQuery(CriteriaOperator operator, boolean selectAll, boolean explodedRequest) {

      List<D> all = findAll();
      int allSize = all.size();
      int searchedCount = client.searchCountAndExtract(SearchRequestDTO.<B>builder()
                                                           .page(Page.builder()
                                                                     .pageNumber(1)
                                                                     .pageSize(Integer.MAX_VALUE)
                                                                     .build())
                                                           .selections(modelFactory.buildValidSelections(selectAll))
                                                           .filters(modelFactory.buildValidUniqueFilters(operator, all, explodedRequest))
                                                           .build());

      SoftAssertions softly = new SoftAssertions();
      switch (operator) {
        case EQUALS:
          softly.assertThat(searchedCount).isEqualTo(explodedRequest ? allSize : 1);
          break;
        case NOT_EQUALS:
          softly.assertThat(searchedCount).isEqualTo(explodedRequest && allSize > 1 ? allSize : allSize - 1);
          break;
        case IN:
          softly.assertThat(searchedCount).isEqualTo(all.size());
          break;
        case NOT_IN:
          softly.assertThat(searchedCount).isEqualTo(explodedRequest && allSize > 1 ? allSize : 0);
          break;
        default:
          throw new IllegalArgumentException("Unknown operator: " + operator);
      }
      softly.assertAll();
    }

    /**
     * Tests if a list of entities can be retrieved by the API via posting search filters.
     */
    @ParameterizedTest
    @MethodSource("searchQueryParameterProvider")
    void canPostSearchExistsQuery(CriteriaOperator operator, boolean selectAll, boolean explodedRequest) {

      List<D> all = findAll();
      int allSize = all.size();
      boolean exists = client.searchExistsAndExtract(SearchRequestDTO.<B>builder()
                                                         .page(Page.builder()
                                                                   .pageNumber(1)
                                                                   .pageSize(Integer.MAX_VALUE)
                                                                   .build())
                                                         .selections(modelFactory.buildValidSelections(selectAll))
                                                         .filters(modelFactory.buildValidUniqueFilters(operator, all, explodedRequest)).build());
      switch (operator) {
        case NOT_IN:
          assertEquals(exists, explodedRequest && allSize > 1);
          break;
        case NOT_EQUALS:
          assertFalse(exists && allSize == 1);
          break;
        default:
          assertTrue(exists);
          break;
      }
    }
  }

}
