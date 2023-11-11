package dev.springharvest.library.domains.publishers.integration.utils.factories;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.testing.domains.integration.search.factories.AbstractSearchModelFactoryImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublisherSearchModelFactory
    extends AbstractSearchModelFactoryImpl<PublisherDTO, PublisherEntity, PublisherFilterRequestDTO> {

  @Autowired
  public PublisherSearchModelFactory(EntityMetadata<PublisherEntity> entityMetadata) {
    super(entityMetadata);
  }

  @Override
  public Set<PublisherFilterRequestDTO> buildValidUniqueFilters(CriteriaOperator operator, List<PublisherDTO> models, boolean explodeRequest) {
    if (explodeRequest) {
      return models.stream().map(model -> PublisherFilterRequestDTO.builder()
          .publisher(PublisherFilterDTO.builder()
                         .id(FilterParameterDTO.builder()
                                 .values(List.of(model.getId()))
                                 .operator(operator)
                                 .build())
                         .name(FilterParameterDTO.builder()
                                   .values(List.of(model.getName()))
                                   .operator(operator)
                                   .build())
                         .build())
          .build()).collect(Collectors.toSet());
    } else {
      List<UUID> ids = new LinkedList<>();
      List<String> names = new LinkedList<>();
      models.forEach(model -> {
        ids.add(model.getId());
        names.add(model.getName());
      });
      return Set.of(PublisherFilterRequestDTO.builder()
                        .publisher(PublisherFilterDTO.builder()
                                       .id(FilterParameterDTO.builder()
                                               .values(ids)
                                               .operator(operator)
                                               .build())
                                       .name(FilterParameterDTO.builder()
                                                 .values(names)
                                                 .operator(operator)
                                                 .build())
                                       .build())
                        .build());
    }
  }
}