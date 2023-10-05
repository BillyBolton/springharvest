package dev.springharvest.library.domains.books.mappers.search.tuples;

import dev.springharvest.library.domains.authors.mappers.search.tuples.AuthorRootTupleTransformer;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.publishers.mappers.search.tuples.PublisherRootTupleTransformer;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookTupleTransformer extends BookRootTupleTransformer {

  private final AuthorRootTupleTransformer authorRootTupleTransformer;
  private final PublisherRootTupleTransformer publisherRootTupleTransformer;

  @Autowired
  public BookTupleTransformer(BookEntityMetadata entityMetadata,
                              AuthorRootTupleTransformer authorTupleTransformer,
                              PublisherRootTupleTransformer publisherTupleTransformer) {
    super(entityMetadata);
    this.authorRootTupleTransformer = authorTupleTransformer;
    this.publisherRootTupleTransformer = publisherTupleTransformer;
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

}
