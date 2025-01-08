package dev.springharvest.library.domains.authors.graphql;

import dev.springharvest.crud.domains.base.graphql.AbstractGraphQLCrudController;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import java.util.*;

import dev.springharvest.shared.constants.DataPaging;
import dev.springharvest.shared.constants.PageData;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AuthorGraphQLController extends AbstractGraphQLCrudController<AuthorEntity, UUID> {

  protected AuthorGraphQLController() {
    super(AuthorEntity.class, UUID.class);
  }

  @QueryMapping
  public PageData<AuthorEntity> searchAuthors(@Argument Map<String, Object> filter, @Argument Map<String, Object> clause, @Argument DataPaging paging, DataFetchingEnvironment environment) {
    return search(filter, clause, paging, environment);
  }

  @QueryMapping
  public long countAuthors(@Argument Map<String, Object> filter, @Argument Map<String, Object> clause, @Argument List<String> fields) {
    return count(filter, clause, fields);
  }
}
