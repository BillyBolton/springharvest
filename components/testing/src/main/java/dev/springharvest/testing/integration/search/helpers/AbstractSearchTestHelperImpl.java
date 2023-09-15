package dev.springharvest.testing.integration.search.helpers;

import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.IUriFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSearchTestHelperImpl<D extends BaseDTO<K>, E extends BaseEntity<K>, K,
        B extends BaseFilterRequestDTO>
        implements ISearchTestHelper<D, E, K, B> {

    protected RestClientImpl restClient;
    protected IUriFactory uriFactory;

    // Do not remove Autowired here
    @Autowired(required = true)
    protected AbstractSearchTestHelperImpl(RestClientImpl restClient, IUriFactory uriFactory) {
        this.restClient = restClient;
        this.uriFactory = uriFactory;

    }

    @Override
    public ValidatableResponse search(SearchRequestDTO<B> filters) {
        return restClient.postAndThen(uriFactory.getPostSearchUri(), filters);
    }

    public Map<String, List<SelectionDTO>> buildValidSelections() {

        Map<String, List<SelectionDTO>> selections = new HashMap<>();

        selections.put("id", List.of(SelectionDTO.builder().alias(getIdPath()).build()));
        selections.put("empty", List.of());
        selections.put("all", buildValidSelections(false));

        return selections;

    }

    @Override
    public List<SearchRequestDTO> buildValidSearchRequestDTOs() {

        List<SearchRequestDTO> requests = new LinkedList<>();

        Map<String, List<SelectionDTO>> selections = buildValidSelections();

        // TODO make parameterized. Only using the query that selects id for now.
        requests.add(SearchRequestDTO.<B>builder()
                                     .selections(selections.get("id"))
                                     .filters(Set.of(buildValidFilters()))
                                     .build());

        return requests;
    }

}
