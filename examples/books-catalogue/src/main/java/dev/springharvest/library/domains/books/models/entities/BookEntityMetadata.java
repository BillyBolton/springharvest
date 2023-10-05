package dev.springharvest.library.domains.books.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springhavest.common.models.entities.BaseEntity_;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEntityMetadata extends EntityMetadata<BookEntity> {

  @Autowired
  protected BookEntityMetadata(AuthorEntityMetadata authorEntityMetadata,
                               PublisherEntityMetadata publisherEntityMetadata) {
    super(Paths.DOMAIN_SINGULAR,
          Paths.DOMAIN_PLURAL,
          Paths.Maps.ROOTS,
          Paths.Maps.ROOT_PATH_CLAZZ_MAP,
          authorEntityMetadata.getPathClazzMap(),
          publisherEntityMetadata.getPathClazzMap());
  }

  public static class Paths {

    public static final String DOMAIN_SINGULAR = "book";
    public static final String DOMAIN_PLURAL = "books";
    public static final String BOOK_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
    public static final String BOOK_TITLE = DOMAIN_SINGULAR + "." + BookEntity_.TITLE;

    private Paths() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    private static class Maps {

      private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PublisherEntity.class);
      private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(BOOK_ID, UUID.class,
                                                                              BOOK_TITLE, String.class);

      private Maps() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }
    }

  }


}
