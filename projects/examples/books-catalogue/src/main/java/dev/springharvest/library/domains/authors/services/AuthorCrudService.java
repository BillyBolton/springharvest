package dev.springharvest.library.domains.authors.services;

import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.persistence.IAuthorCrudRepository;
import java.util.UUID;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.persistence.IBookCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorCrudService extends AbstractCrudService<AuthorEntity, UUID> {

  @Autowired
  protected AuthorCrudService(IAuthorCrudRepository baseRepository) {
    super(baseRepository);
  }
}
