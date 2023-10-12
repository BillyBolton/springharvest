package dev.springharvest.search.domains.embeddables.traces.users.models.entities;

import static dev.springharvest.errors.constants.ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE;
import static dev.springharvest.search.domains.embeddables.traces.dates.models.entities.TraceDatesMetadata.Constants.Paths.DOMAIN_SINGULAR;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.UUIDTraceUsersEntity;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.UUIDTraceUsersEntity_;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;

@Component
public class TraceUsersMetadata extends EntityMetadata<UUIDTraceUsersEntity> {

  protected TraceUsersMetadata() {
    super(UUIDTraceUsersEntity.class,
          Constants.Paths.DOMAIN_SINGULAR,
          Constants.Paths.DOMAIN_PLURAL,
          Constants.Paths.Maps.ROOTS,
          Constants.Paths.Maps.ROOT_PATH_CLAZZ_MAP,
          Constants.Paths.Maps.ROOT_MAPPING_FUNCTIONS);
  }


  @Override
  public Class<?> getClazz(String path) {
    return super.getClazz(scrubPrefix(path));
  }

  private static String scrubPrefix(String path) {
    String[] pathArray = path.split("\\.");
    if (!pathArray[0].equals(DOMAIN_SINGULAR)) {
      String[] newPathArray = new String[pathArray.length - 1];
      System.arraycopy(pathArray, 1, newPathArray, 0, pathArray.length - 1);
      path = String.join(".", newPathArray);
    }
    return path;
  }

  public static class Constants {

    private Constants() {
      throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Paths {

      public static final String DOMAIN_SINGULAR = "traceUsers";
      public static final String DOMAIN_PLURAL = "traceUsers";
      public static final String CREATED_BY = DOMAIN_SINGULAR + "." + UUIDTraceUsersEntity_.CREATED_BY;
      public static final String UPDATED_BY = DOMAIN_SINGULAR + "." + UUIDTraceUsersEntity_.UPDATED_BY;


      private Paths() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_MESSAGE);
      }

      private static class Maps {

        private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, TraceDatesEntity.class);
        private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(CREATED_BY, UUID.class,
                                                                                UPDATED_BY, UUID.class
                                                                               );

        private static final Map<String, BiConsumer<UUIDTraceUsersEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of(
            CREATED_BY, (entity, value) -> entity.setCreatedBy((UUID) value),
            UPDATED_BY, (entity, value) -> entity.setUpdatedBy((UUID) value)
                                                                                                                  );

        private Maps() {
          throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
      }
    }
  }
}
