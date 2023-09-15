package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.library.domains.publishers.mappers.PublisherSearchMapper;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.domains.publishers.persistence.PublisherSearchRepository;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.service.AbstractSearchService;
import dev.springharvest.shared.domains.publishers.mappers.IPublisherMapper;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PublisherSearchService
        extends AbstractSearchService<PublisherDTO, PublisherEntity, UUID, PublisherFilterRequestDTO,
        PublisherFilterRequestBO, PublisherFilterDTO, PublisherFilterBO> {

    @Autowired
    protected PublisherSearchService(IPublisherMapper baseMapper, PublisherSearchMapper filterMapper,
                                     PublisherSearchRepository searchRepository) {
        super(baseMapper, filterMapper, searchRepository);
    }

    @Override
    protected Set<PublisherFilterRequestDTO> buildUniqueFilters(PublisherDTO dto) {
        Set<PublisherFilterRequestDTO> filters = new HashSet<>();

        if (dto.getId() != null) {
            filters.add(PublisherFilterRequestDTO.builder()
                                                 .publisher(PublisherFilterDTO.builder()
                                                                              .id(FilterParameterDTO.builder()
                                                                                                    .values(Set.of(
                                                                                                            dto.getId()))
                                                                                                    .build())
                                                                              .build())
                                                 .build());
        }

        return filters;
    }

}
