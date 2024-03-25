package dev.springharvest.library.domains.authors.rest;

import dev.springharvest.library.domains.authors.constants.AuthorConstants;
import dev.springharvest.library.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.library.domains.authors.services.AuthorSearchService;
import dev.springharvest.search.domains.base.rest.AbstractSearchController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = AuthorConstants.Controller.TAG)
@RequestMapping(AuthorConstants.Controller.DOMAIN_CONTEXT)
public class AuthorSearchControllerImpl
    extends AbstractSearchController<AuthorDTO, AuthorEntity, UUID, AuthorFilterRequestDTO, AuthorFilterRequestBO, AuthorFilterDTO,
    AuthorFilterBO> {

  protected AuthorSearchControllerImpl(IAuthorMapper baseModelMapper, AuthorSearchService baseService) {
    super(baseModelMapper, baseService);
  }

}
