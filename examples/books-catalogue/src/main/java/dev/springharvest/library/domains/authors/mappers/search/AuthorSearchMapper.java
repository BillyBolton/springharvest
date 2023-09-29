package dev.springharvest.library.domains.authors.mappers.search;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.library.global.GlobalClazzResolver;
import dev.springharvest.search.mappers.queries.ISearchMapper;
import java.util.UUID;
import lombok.Getter;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class AuthorSearchMapper
    implements ISearchMapper<AuthorEntity, UUID, AuthorFilterRequestDTO, AuthorFilterRequestBO, AuthorFilterDTO, AuthorFilterBO> {

  @Autowired
  private GlobalClazzResolver globalClazzResolver;

  @Autowired
  private AuthorEntityMetadata entityMetadata;

}
