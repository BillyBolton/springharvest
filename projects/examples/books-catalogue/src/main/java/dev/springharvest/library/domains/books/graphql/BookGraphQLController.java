package dev.springharvest.library.domains.books.graphql;

import dev.springharvest.crud.domains.base.graphql.AbstractGraphQLCrudController;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import java.util.*;

import dev.springharvest.shared.constants.Aggregates;
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
public class BookGraphQLController extends AbstractGraphQLCrudController<BookEntity, UUID> {

  protected BookGraphQLController() {
      super(BookEntity.class, UUID.class);
  }

    @QueryMapping
    public PageData<BookEntity> searchBooks(@Argument Map<String, Object> filter, @Argument Map<String, Object> clause, @Argument DataPaging paging, DataFetchingEnvironment environment) {
        return search(filter, clause, paging, environment);
    }

    @QueryMapping
    public Object complexBooksSearch(@Argument Map<String, Object> filter, @Argument Map<String, Object> clause, @Argument List<String> fields, @Argument DataPaging paging, @Argument Aggregates aggregates) {
        return search(filter, clause, fields, aggregates, paging);
    }

    @QueryMapping
    public long countBooks(@Argument Map<String, Object> filter, @Argument Map<String, Object> clause, @Argument List<String> fields) {
        return count(filter, clause, fields);
    }
}
