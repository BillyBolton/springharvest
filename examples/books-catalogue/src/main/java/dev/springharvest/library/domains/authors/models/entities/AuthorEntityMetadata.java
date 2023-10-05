package dev.springharvest.library.domains.authors.models.entities;

import static dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata.Paths.DOMAIN_SINGULAR;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springhavest.common.models.entities.BaseEntity_;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AuthorEntityMetadata extends EntityMetadata<AuthorEntity> {

  public AuthorEntityMetadata() {
    super(Paths.AUTHOR,
          Paths.AUTHORS,
          Paths.Maps.ROOTS,
          Paths.Maps.ROOT_PATH_CLAZZ_MAP);
  }

  public static class Paths {

    public static final String AUTHORS = "authors";

    public static final String AUTHOR = "author";

    public static final String AUTHOR_ID = AUTHOR + "." + BaseEntity_.ID;
    public static final String AUTHOR_NAME = AUTHOR + "." + AuthorEntity_.NAME;

    private Paths() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    private static class Maps {

      private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PublisherEntity.class);
      private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(AUTHOR_ID, UUID.class,
                                                                              AUTHOR_NAME, String.class);

      private Maps() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }
    }

  }

}
