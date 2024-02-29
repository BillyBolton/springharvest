package dev.springharvest.library.domains.authors.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity_;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;

@Component
public class AuthorEntityMetadata extends EntityMetadata<AuthorEntity> {

  public AuthorEntityMetadata() {
    super(AuthorEntity.class,
          Constants.Paths.DOMAIN_SINGULAR,
          Constants.Paths.DOMAIN_PLURAL,
          Constants.Paths.Maps.ROOTS,
          Constants.Paths.Maps.ROOT_PATH_CLAZZ_MAP,
          Constants.Paths.Maps.ROOT_MAPPING_FUNCTIONS);
  }

  private static class Constants {

    private Constants() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    private static class Paths {

      private static final String DOMAIN_SINGULAR = "author";
      private static final String DOMAIN_PLURAL = "authors";

      private static final String AUTHOR_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
      private static final String AUTHOR_NAME = DOMAIN_SINGULAR + "." + AuthorEntity_.NAME;

      private Paths() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }

      private static class Maps {

        private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PublisherEntity.class);
        private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(AUTHOR_ID, UUID.class,
                                                                                AUTHOR_NAME, String.class);
        private static final Map<String, BiConsumer<AuthorEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of(
            AUTHOR_ID, (entity, value) -> entity.setId((UUID) value),
            AUTHOR_NAME, (entity, value) -> entity.setName((String) value)
                                                                                                          );

        private Maps() {
          throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
      }
    }
  }

}
