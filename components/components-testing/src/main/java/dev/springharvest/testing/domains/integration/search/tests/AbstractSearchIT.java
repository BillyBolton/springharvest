package dev.springharvest.testing.domains.integration.search.tests;

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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
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

      List<D> all = findAll();
      Map<K, D> allMap = all.stream().map(model -> Map.entry(model.getId(), model)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      Map<K, D> firstOfAllMap = all.subList(0, 1).stream().map(model -> Map.entry(model.getId(), model)).collect(Collectors.toMap(Map.Entry::getKey,
                                                                                                                                  Map.Entry::getValue));
      List<D> searched = client.searchAndExtract(SearchRequestDTO.<B>builder()
                                                     .page(Page.builder()
                                                               .pageNumber(1)
                                                               .pageSize(Integer.MAX_VALUE)
                                                               .build())
                                                     .selections(modelFactory.buildValidSelections(selectAll))
                                                     .filters(Set.of(modelFactory.buildValidFilters(operator, all)))
                                                     .build());
      SoftAssertions softly = new SoftAssertions();
      switch (operator) {
        case EQUALS:
          softly.assertThat(searched.size()).isEqualTo(firstOfAllMap.size());
          softly.assertThat(searched.stream().collect(Collectors.toMap(BaseDTO::getId, dto -> dto))).containsAllEntriesOf(firstOfAllMap);
          break;
        case NOT_EQUALS:
          softly.assertThat(searched.size()).isEqualTo(all.size() - 1);
          all.forEach(dto -> softly.assertThat(allMap).containsKey(dto.getId()));
          break;
        case IN:
          softly.assertThat(searched.size()).isEqualTo(all.size());
          softly.assertThat(searched.stream().collect(Collectors.toMap(BaseDTO::getId, dto -> dto))).containsAllEntriesOf(allMap);
          break;
        case NOT_IN:
          softly.assertThat(searched.size()).isEqualTo(0);
          searched.forEach(dto -> softly.assertThat(allMap).doesNotContainKeys(dto.getId()));
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
    void canPostSearchCountQuery(CriteriaOperator operator, boolean selectAll) {

      List<D> all = findAll();
      int searchedCount = client.searchCountAndExtract(SearchRequestDTO.<B>builder()
                                                           .page(Page.builder()
                                                                     .pageNumber(1)
                                                                     .pageSize(Integer.MAX_VALUE)
                                                                     .build())
                                                           .selections(modelFactory.buildValidSelections(selectAll))
                                                           .filters(Set.of(modelFactory.buildValidFilters(operator, all)))
                                                           .build());

      SoftAssertions softly = new SoftAssertions();
      switch (operator) {
        case EQUALS:
          softly.assertThat(searchedCount).isEqualTo(1);
          break;
        case NOT_EQUALS:
          softly.assertThat(searchedCount).isEqualTo(all.size() - 1);
          break;
        case IN:
          softly.assertThat(searchedCount).isEqualTo(all.size());
          break;
        case NOT_IN:
          softly.assertThat(searchedCount).isEqualTo(0);
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
    void canPostSearchExistsQuery(CriteriaOperator operator, boolean selectAll) {

      List<D> all = findAll();
      boolean exists = client.searchExistsAndExtract(SearchRequestDTO.<B>builder()
                                                         .page(Page.builder()
                                                                   .pageNumber(1)
                                                                   .pageSize(Integer.MAX_VALUE)
                                                                   .build())
                                                         .selections(modelFactory.buildValidSelections(selectAll))
                                                         .filters(Set.of(modelFactory.buildValidFilters(operator, all))).build());

      if (operator.equals(CriteriaOperator.NOT_IN)) {
        assertFalse(exists);
      } else if (operator.equals(CriteriaOperator.NOT_EQUALS) && (all.size() == 1)) {
        assertFalse(exists);
      } else {
        assertTrue(exists);
      }

    }
  }

}
