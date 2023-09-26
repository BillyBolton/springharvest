package dev.springharvest.library.domains.authors.rest;

import dev.springharvest.crud.rest.AbstractCrudController;
import dev.springharvest.library.domains.authors.constants.AuthorConstants;
import dev.springharvest.library.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.services.AuthorCrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = AuthorConstants.Controller.TAG)
@RequestMapping(AuthorConstants.Controller.DOMAIN_CONTEXT)
public class AuthorCrudControllerImpl extends AbstractCrudController<AuthorDTO, AuthorEntity, UUID> {

  protected AuthorCrudControllerImpl(IAuthorMapper baseModelMapper, AuthorCrudService baseService) {
    super(baseModelMapper, baseService);
  }

}
