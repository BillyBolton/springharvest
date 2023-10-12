package dev.springharvest.testing.domains.integration.search.factories;

import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import java.util.List;

public interface ISearchModelFactory<B extends BaseFilterRequestDTO> {

  String getIdPath();

  B buildValidFilters();

  List<SelectionDTO> buildValidSelections(boolean isEmpty);

  List<SearchRequestDTO<B>> buildValidSearchRequestDTOs();

}
