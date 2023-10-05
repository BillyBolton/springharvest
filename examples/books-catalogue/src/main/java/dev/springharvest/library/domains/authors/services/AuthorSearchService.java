package dev.springharvest.library.domains.authors.services;

import dev.springharvest.library.domains.authors.mappers.search.AuthorSearchMapper;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.library.domains.authors.persistence.AuthorSearchRepository;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springharvest.search.service.AbstractSearchService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorSearchService
    extends AbstractSearchService<AuthorEntity, UUID, AuthorFilterRequestDTO, AuthorFilterRequestBO,
    AuthorFilterDTO, AuthorFilterBO> {

  @Autowired
  protected AuthorSearchService(EntityMetadata<AuthorEntity> entityMetadata, AuthorSearchMapper filterMapper, AuthorSearchRepository searchRepository) {
    super(entityMetadata, filterMapper, searchRepository);
  }

}
