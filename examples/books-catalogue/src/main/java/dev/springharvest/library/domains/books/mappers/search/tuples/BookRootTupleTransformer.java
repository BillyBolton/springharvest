package dev.springharvest.library.domains.books.mappers.search.tuples;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.search.mappers.transformers.AbstractBaseTupleTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookRootTupleTransformer extends AbstractBaseTupleTransformer<BookEntity> {

  @Autowired
  public BookRootTupleTransformer(BookEntityMetadata entityMetadata) {
    super(entityMetadata);
  }

}
