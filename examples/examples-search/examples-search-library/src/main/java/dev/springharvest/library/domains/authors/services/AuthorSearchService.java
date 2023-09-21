package dev.springharvest.library.domains.authors.services;

import dev.springharvest.library.domains.authors.mappers.AuthorSearchMapper;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.library.domains.authors.persistence.AuthorSearchRepository;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.service.AbstractSearchService;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorSearchService
    extends AbstractSearchService<AuthorEntity, UUID, AuthorFilterRequestDTO, AuthorFilterRequestBO,
    AuthorFilterDTO, AuthorFilterBO> {

  @Autowired
  protected AuthorSearchService(AuthorSearchMapper filterMapper, AuthorSearchRepository searchRepository) {
    super(filterMapper, searchRepository);
  }

  @Override
  protected Set<AuthorFilterRequestDTO> buildUniqueFilters(AuthorEntity entity) {
    Set<AuthorFilterRequestDTO> filters = new HashSet<>();

    if (ObjectUtils.isNotEmpty(entity.getId())) {
      filters.add(AuthorFilterRequestDTO.builder()
                      .author(AuthorFilterDTO.builder()
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
