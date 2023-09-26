package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.library.domains.publishers.mappers.search.PublisherSearchMapper;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.domains.publishers.persistence.PublisherSearchRepository;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.service.AbstractSearchService;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherSearchService
    extends AbstractSearchService<PublisherEntity, UUID, PublisherFilterRequestDTO, PublisherFilterRequestBO,
    PublisherFilterDTO, PublisherFilterBO> {

  @Autowired
  protected PublisherSearchService(PublisherSearchMapper filterMapper, PublisherSearchRepository searchRepository) {
    super(filterMapper, searchRepository);
  }

  @Override
  protected Set<PublisherFilterRequestDTO> buildUniqueFilters(PublisherEntity entity) {
    Set<PublisherFilterRequestDTO> filters = new HashSet<>();

    if (ObjectUtils.isNotEmpty(entity.getId())) {
      filters.add(PublisherFilterRequestDTO.builder()
                      .publisher(PublisherFilterDTO.builder()
                                     .id(FilterParameterDTO.builder()
                                             .values(Set.of(
                                                 entity.getId()))
                                             .build())
                                     .build())
                      .build());
    }

    return filters;
  }

}
