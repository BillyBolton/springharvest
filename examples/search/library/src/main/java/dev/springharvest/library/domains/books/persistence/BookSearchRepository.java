package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.library.domains.books.mappers.tuples.BookRootTupleTransformer;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDaoImpl;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class BookSearchRepository extends AbstractCriteriaSearchDaoImpl<BookEntity, UUID, BookFilterRequestBO> {

    BookSearchRepository(BookRootTupleTransformer tupleTransformer, BookEntityMetadata entityMetadata) {
        super(entityMetadata.getDomainName(), tupleTransformer);
    }

}

