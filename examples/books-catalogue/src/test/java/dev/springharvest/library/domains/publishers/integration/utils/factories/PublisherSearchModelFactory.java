package dev.springharvest.library.domains.publishers.integration.utils.helpers;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.testing.integration.search.factories.AbstractSearchModelFactoryImpl;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublisherSearchModelFactoryImpl
    extends AbstractSearchModelFactoryImpl<PublisherEntity, PublisherFilterRequestDTO> {

  @Autowired
  public PublisherSearchModelFactoryImpl(EntityMetadata<PublisherEntity> entityMetadata) {
    super(entityMetadata);
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

}