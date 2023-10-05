package dev.springharvest.library.global;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.search.global.IGlobalClazzResolver;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springharvest.search.model.entities.IEntityMetadata;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GlobalClazzResolver implements IGlobalClazzResolver {

  private final Map<String, IEntityMetadata<?>> entityMetadataMap;

  @Autowired
  public GlobalClazzResolver(EntityMetadata<AuthorEntity> authorMetadata,
                             EntityMetadata<PublisherEntity> publisherMetadata,
                             EntityMetadata<BookEntity> bookMetadata) {

    this.entityMetadataMap = new HashMap<>();
    entityMetadataMap.put(authorMetadata.getDomainName(), authorMetadata);
    entityMetadataMap.put(publisherMetadata.getDomainName(), publisherMetadata);
    entityMetadataMap.put(bookMetadata.getDomainName(), bookMetadata);
  }

}
