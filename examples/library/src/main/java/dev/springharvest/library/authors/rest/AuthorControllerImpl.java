package dev.springharvest.library.authors.rest;

import dev.springharvest.crud.rest.AbstractBaseCrudController;
import dev.springharvest.library.authors.constants.AuthorConstants;
import dev.springharvest.library.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.authors.models.entities.AuthorEntity;
import dev.springharvest.library.authors.services.AuthorService;
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

    protected AuthorControllerImpl(AuthorService baseService) {
        super(baseService);
    }

}
