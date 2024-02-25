package dev.springharvest.library.domains.books.controllers.graphql;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.services.BookCrudService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookGraphQLController {

  private final BookCrudService baseService;

  @Autowired
  protected BookGraphQLController(BookCrudService baseService) {
    this.baseService = baseService;
  }

  @QueryMapping()
  List<BookEntity> books() {
    //TODO: Fix
    return baseService.findAll(Pageable.unpaged()).getContent();
  }

  @QueryMapping()
  Optional<BookEntity> bookById(@Argument UUID id) {
    return baseService.findById(id);
  }

  @QueryMapping()
  public List<BookEntity> booksByAuthor(@Argument UUID id) {
    return baseService.findByAuthorId(id);
  }

  @QueryMapping()
  public List<BookEntity> booksByPublisher(@Argument UUID id) {
    return baseService.findByPublisherId(id);
  }
}
