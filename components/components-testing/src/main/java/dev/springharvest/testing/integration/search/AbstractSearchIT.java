package dev.springharvest.testing.integration.search;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.pages.Page;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestHelperImpl;
import dev.springharvest.testing.integration.search.helpers.ISearchTestHelper;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractSearchIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K, B extends BaseFilterRequestDTO>
        implements ISearchIT<D, E, K, B> {

    protected RestClientImpl clientHelper;
    protected ISearchTestHelper<D, E, K, B> testHelper;

    protected AbstractSearchIT(RestClientImpl clientHelper, AbstractSearchTestHelperImpl<D, E, K, B> testHelper) {
        this.clientHelper = clientHelper;
        this.testHelper = testHelper;
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

            int expectedResponseCode = 200;

            SearchRequestDTO.SearchRequestDTOBuilder request = SearchRequestDTO.builder()
                                                                               .page(Page.builder().build())
                                                                               .selections(
                                                                                       testHelper.buildValidSelections(
                                                                                               selectAll))
                                                                               .filters(
                                                                                       Set.of(testHelper.buildValidFilters()));

            ValidatableResponse response = testHelper.search(request.build());
            List<D> found = response.statusCode(expectedResponseCode)
                                    .extract()
                                    .body()
                                    .jsonPath()
                                    .getList("", testHelper.getClassType());

            assertEquals(1, found.size());
        }

    }


}
