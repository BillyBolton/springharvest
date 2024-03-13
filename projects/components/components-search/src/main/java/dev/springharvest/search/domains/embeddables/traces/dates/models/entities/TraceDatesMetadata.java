package dev.springharvest.search.domains.embeddables.traces.dates.models.entities;

import static dev.springharvest.errors.constants.ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE;
import static dev.springharvest.search.domains.embeddables.traces.dates.models.entities.TraceDatesMetadata.Constants.Paths.DOMAIN_SINGULAR;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity_;
import java.util.Date;
import java.util.Map;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;

@Component
public class TraceDatesMetadata extends EntityMetadata<TraceDatesEntity> {

  protected TraceDatesMetadata() {
    super(TraceDatesEntity.class,
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

      public static final String DOMAIN_SINGULAR = "traceDates";
      public static final String DOMAIN_PLURAL = "traceDates";
      public static final String DATE_CREATED = DOMAIN_SINGULAR + "." + TraceDatesEntity_.DATE_CREATED;
      public static final String DATE_UPDATED = DOMAIN_SINGULAR + "." + TraceDatesEntity_.DATE_UPDATED;


      private Paths() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_MESSAGE);
      }

      private static class Maps {

        private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, TraceDatesEntity.class);
        private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(DATE_CREATED, Date.class,
                                                                                DATE_UPDATED, Date.class
                                                                               );

        private static final Map<String, BiConsumer<TraceDatesEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of(
            DATE_CREATED, (entity, value) -> entity.setDateCreated((Date) value),
            DATE_UPDATED, (entity, value) -> entity.setDateUpdated((Date) value)
                                                                                                              );

        private Maps() {
          throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
      }
    }
  }
}
