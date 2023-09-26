package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.library.domains.books.mappers.search.tuples.BookRootTupleTransformer;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDao;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class BookSearchRepository extends AbstractCriteriaSearchDao<BookEntity, UUID, BookFilterRequestBO> {

  BookSearchRepository(BookRootTupleTransformer tupleTransformer, BookEntityMetadata entityMetadata) {
    super(entityMetadata.getDomainName(), tupleTransformer);
  }

}

