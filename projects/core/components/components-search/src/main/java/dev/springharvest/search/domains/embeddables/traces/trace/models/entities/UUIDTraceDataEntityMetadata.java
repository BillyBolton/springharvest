package dev.springharvest.search.domains.embeddables.traces.trace.models.entities;

import static dev.springharvest.errors.constants.ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE;
import static dev.springharvest.search.domains.embeddables.traces.trace.models.entities.UUIDTraceDataEntityMetadata.Constants.Paths.DOMAIN_SINGULAR;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.UUIDTraceDataEntity;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.UUIDTraceDataEntity_;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.UUIDTraceUsersEntity;
import dev.springharvest.shared.utils.MetadataUtils;
import java.util.Map;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UUIDTraceDataEntityMetadata extends EntityMetadata<UUIDTraceDataEntity> {

  @Autowired
  protected UUIDTraceDataEntityMetadata(EntityMetadata<TraceDatesEntity> traceDatesMetadata,
                                        EntityMetadata<UUIDTraceUsersEntity> traceUsersMetadata) {
    super(UUIDTraceDataEntity.class,
          Constants.Paths.DOMAIN_SINGULAR,
          Constants.Paths.DOMAIN_PLURAL,
          Constants.Paths.Maps.ROOTS,
          Constants.Paths.Maps.ROOT_PATH_CLAZZ_MAP,
          Constants.Paths.Maps.ROOT_MAPPING_FUNCTIONS,
          traceDatesMetadata.getPathClazzMap(),
          traceUsersMetadata.getPathClazzMap());
  }

  @Override
  public Class<?> getClazz(String path) {
    return super.getClazz(MetadataUtils.scrubPrefix(path, DOMAIN_SINGULAR));
  }

  public static class Constants {

    private Constants() {
      throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Paths {

      public static final String DOMAIN_SINGULAR = "traceData";
      public static final String DOMAIN_PLURAL = "traceData";
      public static final String TRACE_DATES = DOMAIN_SINGULAR + "." + UUIDTraceDataEntity_.TRACE_DATES;
      public static final String TRACE_USERS = DOMAIN_SINGULAR + "." + UUIDTraceDataEntity_.TRACE_USERS;


      private Paths() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_MESSAGE);
      }

      private static class Maps {

        private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, TraceDataEntity.class,
                                                                  TRACE_DATES, TraceDatesEntity.class,
                                                                  TRACE_USERS, UUIDTraceUsersEntity.class);
        private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of();
        private static final Map<String, BiConsumer<UUIDTraceDataEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of();

        private Maps() {
          throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
      }
    }
  }

}
