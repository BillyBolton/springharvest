package dev.springharvest.library.domains.authors.rest;

import dev.springharvest.crud.rest.AbstractBaseCrudController;
import dev.springharvest.library.domains.authors.services.AuthorCrudService;
import dev.springharvest.shared.domains.authors.constants.AuthorConstants;
import dev.springharvest.shared.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@Tag(name = AuthorConstants.Controller.TAG)
@RequestMapping(AuthorConstants.Controller.DOMAIN_CONTEXT)
public class AuthorControllerImpl extends AbstractBaseCrudController<AuthorDTO, AuthorEntity, UUID> {

    protected AuthorControllerImpl(IAuthorMapper baseModelMapper, AuthorCrudService baseService) {
        super(baseModelMapper, baseService);
    }

}
