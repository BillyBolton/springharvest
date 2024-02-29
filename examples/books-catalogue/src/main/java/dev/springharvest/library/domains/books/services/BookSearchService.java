package dev.springharvest.library.domains.books.services;

import dev.springharvest.library.domains.books.mappers.search.BookSearchMapper;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.queries.BookFilterBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.library.domains.books.persistence.BookSearchRepository;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.services.AbstractSearchService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSearchService
    extends AbstractSearchService<BookEntity, UUID, BookFilterRequestDTO, BookFilterRequestBO, BookFilterDTO,
    BookFilterBO> {

  @Autowired
  protected BookSearchService(EntityMetadata<BookEntity> entityMetadata,
                              BookSearchMapper filterMapper,
                              BookSearchRepository searchRepository) {
    super(entityMetadata, filterMapper, searchRepository);
  }

}
