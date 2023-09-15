package dev.springharvest.library.domains.authors.services;

import dev.springharvest.library.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.library.domains.authors.mappers.IAuthorSearchMapper;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.library.domains.authors.persistence.AuthorSearchRepository;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.service.AbstractSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthorSearchService
        extends AbstractSearchService<AuthorDTO, AuthorEntity, UUID, AuthorFilterRequestDTO, AuthorFilterRequestBO,
        AuthorFilterDTO, AuthorFilterBO> {
    
    @Autowired
    protected AuthorSearchService(IAuthorMapper baseMapper, IAuthorSearchMapper filterMapper,
                                  AuthorSearchRepository searchRepository) {
        super(baseMapper, filterMapper, searchRepository);
    }

    @Override
    protected Set<AuthorFilterRequestDTO> buildUniqueFilters(AuthorDTO dto) {
        Set<AuthorFilterRequestDTO> filters = new HashSet<>();

        if (dto.getId() != null) {
            filters.add(AuthorFilterRequestDTO.builder()
                                              .author(AuthorFilterDTO.builder()
                                                                     .id(FilterParameterDTO.builder()
                                                                                           .values(Set.of(dto.getId()))
                                                                                           .build())
                                                                     .build())
                                              .build());
        }

        return filters;
    }

}
