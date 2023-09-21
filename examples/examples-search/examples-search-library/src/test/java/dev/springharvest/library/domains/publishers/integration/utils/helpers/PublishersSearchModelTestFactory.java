package dev.springharvest.library.domains.publishers.integration.utils.helpers;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestFactoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublishersSearchModelTestFactory
    extends AbstractSearchTestFactoryImpl<PublisherDTO, PublisherEntity, UUID, PublisherFilterRequestDTO> {

  @Override
  public String getIdPath() {
    return PublisherEntityMetadata.Paths.PUBLISHER_ID;
  }

  @Override
  public PublisherFilterRequestDTO buildValidFilters() {
    return PublisherFilterRequestDTO.builder()
        .publisher(PublisherFilterDTO.builder()
                       .id(FilterParameterDTO.builder()
                               .values(Set.of(
                                   UUID.fromString(
                                       "00000000-0000-0000-0000-000000000001")))
                               .operator(
                                   CriteriaOperator.EQUALS)
                               .build())
                       .build())
        .build();
  }

  @Override
  public List<SelectionDTO> buildValidSelections(boolean selectAll) {

    if (selectAll) {
      return List.of();
    }
    List<SelectionDTO> selections = new ArrayList<>();
    selections.addAll(List.of(SelectionDTO.builder().alias(PublisherEntityMetadata.Paths.PUBLISHER_ID).build(),
                              SelectionDTO.builder().alias(PublisherEntityMetadata.Paths.PUBLISHER_NAME).build()));

    return selections;
  }

}