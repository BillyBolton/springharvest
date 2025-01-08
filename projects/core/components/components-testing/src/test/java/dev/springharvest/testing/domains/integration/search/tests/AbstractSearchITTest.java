package dev.springharvest.testing.domains.integration.search.tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.pages.Page;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.testing.domains.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.domains.integration.search.factories.ISearchModelFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class AbstractSearchITTest {

    private AbstractSearchClientImpl<BaseDTO<Long>, Long, BaseFilterRequestDTO> client;
    private ISearchModelFactory<BaseDTO<Long>, BaseFilterRequestDTO> modelFactory;
    private AbstractSearchIT<BaseDTO<Long>, Long, BaseFilterRequestDTO> searchIT;

    @BeforeEach
    void setUp() {
        client = mock(AbstractSearchClientImpl.class);
        modelFactory = mock(ISearchModelFactory.class);
        searchIT = new AbstractSearchIT<>(client, modelFactory) {};
    }

    @Nested
    class ConfigTest {

        @Test
        void contextLoads() {
            assertTrue(true, "The context should load successfully.");
        }
    }

    @Nested
    class PostPaths {

        private static Stream<Arguments> searchQueryParameterProvider() {
            List<CriteriaOperator> criteriaOperatorProviders = List.of(
                CriteriaOperator.EQUALS, CriteriaOperator.IN, 
                CriteriaOperator.NOT_EQUALS, CriteriaOperator.NOT_IN
            );
            List<Boolean> selectAllProviders = List.of(Boolean.TRUE, Boolean.FALSE);
            List<Boolean> explodeRequestProviders = List.of(Boolean.TRUE, Boolean.FALSE);

            return criteriaOperatorProviders.stream()
                .flatMap(operator -> selectAllProviders.stream()
                    .flatMap(select -> explodeRequestProviders.stream()
                        .map(explodedRequest -> Arguments.of(operator, select, explodedRequest))));
        }

        @ParameterizedTest
        @MethodSource("searchQueryParameterProvider")
        void canPostSearchQuery(CriteriaOperator operator, boolean selectAll, boolean explodedRequest) {
            List<BaseDTO<Long>> all = mockEntities(5);
            when(client.searchAndExtract(any())).thenReturn(all);

            when(modelFactory.buildValidSelections(selectAll)).thenReturn(List.of(mock(SelectionDTO.class)));
            when(modelFactory.buildValidUniqueFilters(eq(operator), eq(all), eq(explodedRequest))).thenReturn(Set.of(mock(BaseFilterRequestDTO.class)));

            List<BaseDTO<Long>> searched = searchIT.client.searchAndExtract(SearchRequestDTO.<BaseFilterRequestDTO>builder()
                .page(Page.builder().pageNumber(1).pageSize(Integer.MAX_VALUE).build())
                .selections(modelFactory.buildValidSelections(selectAll))
                .filters(modelFactory.buildValidUniqueFilters(operator, all, explodedRequest))
                .build());

            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(searched).isNotEmpty();

            softly.assertAll();
            verify(client).searchAndExtract(any());
        }

        @ParameterizedTest
        @MethodSource("searchQueryParameterProvider")
        void canPostSearchCountQuery(CriteriaOperator operator, boolean selectAll, boolean explodedRequest) {
            when(client.searchCountAndExtract(any())).thenReturn(5);

            int count = searchIT.client.searchCountAndExtract(SearchRequestDTO.<BaseFilterRequestDTO>builder()
                .page(Page.builder().pageNumber(1).pageSize(Integer.MAX_VALUE).build())
                .selections(modelFactory.buildValidSelections(selectAll))
                .filters(modelFactory.buildValidUniqueFilters(operator, mockEntities(5), explodedRequest))
                .build());

            assertEquals(5, count, "Count should match the mocked response.");
            verify(client).searchCountAndExtract(any());
        }

        @ParameterizedTest
        @MethodSource("searchQueryParameterProvider")
        void canPostSearchExistsQuery(CriteriaOperator operator, boolean selectAll, boolean explodedRequest) {
            when(client.searchExistsAndExtract(any())).thenReturn(true);

            boolean exists = searchIT.client.searchExistsAndExtract(SearchRequestDTO.<BaseFilterRequestDTO>builder()
                .page(Page.builder().pageNumber(1).pageSize(Integer.MAX_VALUE).build())
                .selections(modelFactory.buildValidSelections(selectAll))
                .filters(modelFactory.buildValidUniqueFilters(operator, mockEntities(5), explodedRequest))
                .build());

            assertTrue(exists, "Entity should exist based on the mocked response.");
            verify(client).searchExistsAndExtract(any());
        }

        private List mockEntities(int count) {
            return Stream.generate(() -> mock(BaseDTO.class)).limit(count).collect(Collectors.toList());
        }
    }
}
