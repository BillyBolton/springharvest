package dev.springharvest.search.domains.embeddables.traces.trace.mappers.transformers;

import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.search.domains.embeddables.traces.dates.mappers.transformers.TraceDatesTransformer;
import dev.springharvest.search.domains.embeddables.traces.users.mappers.transformers.UUIDTraceUsersTransformer;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.UUIDTraceDataEntity;
import jakarta.persistence.Tuple;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class UUIDTraceDataTransformer extends AbstractBaseTupleTransformer<UUIDTraceDataEntity> {

  private final TraceDatesTransformer traceDatesTransformer;
  private final UUIDTraceUsersTransformer traceUsersTransformer;

  protected UUIDTraceDataTransformer(IEntityMetadata<UUIDTraceDataEntity> entityMetadata,
                                     TraceDatesTransformer traceDatesTransformer,
                                     UUIDTraceUsersTransformer traceUsersTransformer) {
    super(entityMetadata);
    this.traceDatesTransformer = traceDatesTransformer;
    this.traceUsersTransformer = traceUsersTransformer;
  }

  @Override
  public void upsertAssociatedEntities(UUIDTraceDataEntity entity, Tuple tuple) {
    var traceDates = traceDatesTransformer.apply(tuple);
    if (ObjectUtils.isNotEmpty(traceDates) && !traceDates.isEmpty()) {
      entity.setTraceDates(traceDates);
    }
    var traceUsers = traceUsersTransformer.apply(tuple);
    if (ObjectUtils.isNotEmpty(traceUsers) && !traceUsers.isEmpty()) {
      entity.setTraceUsers(traceUsers);
    }
  }

}