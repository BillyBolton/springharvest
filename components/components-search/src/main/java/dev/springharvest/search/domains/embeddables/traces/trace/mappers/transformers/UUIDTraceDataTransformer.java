package dev.springharvest.search.domains.embeddables.traces.trace.mappers.transformers;

import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.UUIDTraceDataEntityAware;
import org.springframework.stereotype.Component;

@Component
public class UUIDTraceDataTransformer extends AbstractBaseTupleTransformer<UUIDTraceDataEntityAware> {

  protected UUIDTraceDataTransformer(IEntityMetadata<UUIDTraceDataEntityAware> entityMetadata) {
    super(entityMetadata);
  }

}