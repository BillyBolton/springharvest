package dev.springharvest.testing.domains.integration.search.factories;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.shared.domains.DomainModel;
import java.util.List;

public interface ISearchModelFactory<D extends DomainModel, B extends BaseFilterRequestDTO> {

  String getIdPath();

  B buildValidFilters(CriteriaOperator operator, List<D> models);

  List<SelectionDTO> buildValidSelections(boolean isEmpty);

  List<SearchRequestDTO<B>> buildValidSearchRequestDTOs(CriteriaOperator operator, List<D> models);

}
