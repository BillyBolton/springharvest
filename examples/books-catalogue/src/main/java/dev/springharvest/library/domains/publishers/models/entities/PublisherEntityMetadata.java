package dev.springharvest.library.domains.publishers.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springhavest.common.models.entities.BaseEntity_;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;

@Component
public class PublisherEntityMetadata extends EntityMetadata<PublisherEntity> {

  protected PublisherEntityMetadata() {
    super(PublisherEntity.class,
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

      private static final String DOMAIN_SINGULAR = "publisher";
      private static final String DOMAIN_PLURAL = "publishers";
      private static final String PUBLISHER_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
      private static final String PUBLISHER_NAME = DOMAIN_SINGULAR + "." + PublisherEntity_.NAME;

      private Paths() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }

      private static class Maps {

        private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(PUBLISHER_ID, UUID.class,
                                                                                PUBLISHER_NAME, String.class);
        private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PublisherEntity.class);

        private static final Map<String, BiConsumer<PublisherEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of(
            PUBLISHER_ID, (entity, value) -> entity.setId((UUID) value),
            PUBLISHER_NAME, (entity, value) -> entity.setName((String) value)
                                                                                                             );

        private Maps() {
          throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
      }
    }
  }
}
