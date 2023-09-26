package dev.springharvest.library.domains.books.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.persistence.IBookCrudRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCrudService extends AbstractBaseCrudService<BookEntity, UUID> {

  @Autowired
  protected BookCrudService(IBookCrudRepository baseRepository) {
    super(baseRepository);
  }

}
