package dev.springharvest.library.domains.authors.controllers.graphql;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.services.AuthorCrudService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorGraphQLController {

  private final AuthorCrudService baseService;

  @Autowired
  protected AuthorGraphQLController(AuthorCrudService baseService) {
    this.baseService = baseService;
  }

  @QueryMapping
  List<AuthorEntity> authors() {
    //TODO: Fix
    return baseService.findAll(Pageable.unpaged()).getContent();
  }

  @QueryMapping
  Optional<AuthorEntity> authorById(@Argument UUID id) {
    return baseService.findById(id);
  }
}
