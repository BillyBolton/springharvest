package dev.springharvest.library.global;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.search.global.IGlobalClazzResolver;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// This class is used to resolve global classes
// It implements the IGlobalClazzResolver interface
@Getter
@Component
public class GlobalClazzResolver implements IGlobalClazzResolver {

  // This map is used to store entity metadata
  private final Map<String, IEntityMetadata<?>> entityMetadataMap;

  // This constructor is used to initialize the entity metadata map
  @Autowired
  public GlobalClazzResolver(EntityMetadata<AuthorEntity> authorMetadata,
                             EntityMetadata<PublisherEntity> publisherMetadata,
                             EntityMetadata<PetEntity> petEntityEntityMetadata,
                             EntityMetadata<BookEntity> bookMetadata) {

    this.entityMetadataMap = new HashMap<>();
    // Adding author metadata to the map
    entityMetadataMap.put(authorMetadata.getDomainName(), authorMetadata);
    // Adding publisher metadata to the map
    entityMetadataMap.put(publisherMetadata.getDomainName(), publisherMetadata);
    // Adding pet metadata to the map
    entityMetadataMap.put(petEntityEntityMetadata.getDomainName(), petEntityEntityMetadata);
    // Adding book metadata to the map
    entityMetadataMap.put(bookMetadata.getDomainName(), bookMetadata);
  }

}