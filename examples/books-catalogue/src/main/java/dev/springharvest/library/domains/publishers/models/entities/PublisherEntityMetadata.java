package dev.springharvest.library.domains.publishers.models.entities;

import static dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata.Constants.Paths.DOMAIN_SINGULAR;
import static dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata.Constants.Paths.PUBLISHER_ID;
import static dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata.Constants.Paths.PUBLISHER_NAME;

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
          Constants.Maps.ROOTS,
          Constants.Maps.ROOT_PATH_CLAZZ_MAP,
          Constants.Maps.ROOT_MAPPING_FUNCTIONS);
  }

  public static class Constants {

    private Constants() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Paths {

      public static final String DOMAIN_SINGULAR = "publisher";
      public static final String DOMAIN_PLURAL = "publishers";
      public static final String PUBLISHER_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
      public static final String PUBLISHER_NAME = DOMAIN_SINGULAR + "." + PublisherEntity_.NAME;

      private Paths() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }
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
