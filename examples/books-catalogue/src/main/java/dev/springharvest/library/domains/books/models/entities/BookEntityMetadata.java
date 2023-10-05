package dev.springharvest.library.domains.books.models.entities;

import static dev.springharvest.library.domains.books.models.entities.BookEntityMetadata.Constants.Paths.BOOK_ID;
import static dev.springharvest.library.domains.books.models.entities.BookEntityMetadata.Constants.Paths.BOOK_TITLE;
import static dev.springharvest.library.domains.books.models.entities.BookEntityMetadata.Constants.Paths.DOMAIN_SINGULAR;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springhavest.common.models.entities.BaseEntity_;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEntityMetadata extends EntityMetadata<BookEntity> {

  @Autowired
  protected BookEntityMetadata(AuthorEntityMetadata authorEntityMetadata,
                               PublisherEntityMetadata publisherEntityMetadata) {
    super(BookEntity.class,
          Constants.Paths.DOMAIN_SINGULAR,
          Constants.Paths.DOMAIN_PLURAL,
          Constants.Maps.ROOTS,
          Constants.Maps.ROOT_PATH_CLAZZ_MAP,
          Constants.Maps.ROOT_MAPPING_FUNCTIONS,
          authorEntityMetadata.getPathClazzMap(),
          publisherEntityMetadata.getPathClazzMap());
  }

  public static class Constants {

    private Constants() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Paths {

      public static final String DOMAIN_SINGULAR = "book";
      public static final String DOMAIN_PLURAL = "books";
      public static final String BOOK_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
      public static final String BOOK_TITLE = DOMAIN_SINGULAR + "." + BookEntity_.TITLE;

      private Paths() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }

    }

    private static class Maps {

      private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PublisherEntity.class);
      private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(BOOK_ID, UUID.class,
                                                                              BOOK_TITLE, String.class);

      private static final Map<String, BiConsumer<BookEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of(
          BOOK_ID, (entity, value) -> entity.setId((UUID) value),
          BOOK_TITLE, (entity, value) -> entity.setTitle((String) value)
                                                                                                      );

      private Maps() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }
    }
  }


}
