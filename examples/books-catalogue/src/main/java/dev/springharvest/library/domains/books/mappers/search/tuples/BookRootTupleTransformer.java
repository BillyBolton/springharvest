package dev.springharvest.library.domains.books.mappers.search.tuples;

import dev.springharvest.library.domains.authors.mappers.search.tuples.AuthorRootTupleTransformer;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.publishers.mappers.search.tuples.PublisherRootTupleTransformer;
import dev.springharvest.search.mappers.transformers.AbstractBaseTupleTransformer;
import jakarta.persistence.Tuple;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookRootTupleTransformer extends AbstractBaseTupleTransformer<BookEntity> {

  private final AuthorRootTupleTransformer authorRootTupleTransformer;
  private final PublisherRootTupleTransformer publisherRootTupleTransformer;

  @Autowired
  public BookRootTupleTransformer(BookEntityMetadata entityMetadata,
                                  AuthorRootTupleTransformer authorTupleTransformer,
                                  PublisherRootTupleTransformer publisherTupleTransformer) {
    super(entityMetadata);
    this.authorRootTupleTransformer = authorTupleTransformer;
    this.publisherRootTupleTransformer = publisherTupleTransformer;
  }

  @Override
  protected BookEntity getNewEntity() {
    return BookEntity.builder().build();
  }

  @Override
  public void upsertAssociatedEntities(BookEntity entity, Tuple tuple) {
    var author = authorRootTupleTransformer.apply(tuple);
    if (author != null) {
      entity.setAuthor(author.isEmpty() ? null : author);
    }

    var publisher = publisherRootTupleTransformer.apply(tuple);
    if (publisher != null) {
      entity.setPublisher(publisher.isEmpty() ? null : publisher);
    }
  }

  @Override
  protected void mapTupleElement(BookEntity entity, String alias, Object value) {
    switch (alias) {
      case BookEntityMetadata.Paths.BOOK_ID -> entity.setId((UUID) value);
      case BookEntityMetadata.Paths.BOOK_TITLE -> entity.setTitle((String) value);
      default -> {
        // continue
      }
    }
  }

}
