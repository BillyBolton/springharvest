package dev.springharvest.library.domains.publishers.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springhavest.common.models.entities.BaseEntity_;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PublisherEntityMetadata extends EntityMetadata<PublisherEntity> {

  protected PublisherEntityMetadata() {
    super(Paths.DOMAIN_SINGULAR,
          Paths.DOMAIN_PLURAL,
          Paths.Maps.ROOTS,
          Paths.Maps.ROOT_PATH_CLAZZ_MAP);
  }

  public static class Paths {

    public static final String DOMAIN_SINGULAR = "publisher";
    public static final String DOMAIN_PLURAL = "publishers";
    public static final String PUBLISHER_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
    public static final String PUBLISHER_NAME = DOMAIN_SINGULAR + "." + PublisherEntity_.NAME;

    private Paths() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    private static class Maps {

      private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(PUBLISHER_ID, UUID.class,
                                                                              PUBLISHER_NAME, String.class);
      private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PublisherEntity.class);

      private Maps() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
      }
    }


  }


}
