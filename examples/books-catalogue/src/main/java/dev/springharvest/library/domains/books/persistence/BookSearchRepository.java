package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.library.domains.books.mappers.search.tuples.BookTupleTransformer;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.search.domains.base.persistence.AbstractCriteriaSearchDao;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class BookSearchRepository extends AbstractCriteriaSearchDao<BookEntity, UUID, BookFilterRequestBO> {

  BookSearchRepository(BookTupleTransformer tupleTransformer, BookEntityMetadata entityMetadata) {
    super(entityMetadata.getDomainName(), tupleTransformer);
  }

}

