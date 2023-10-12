package dev.springharvest.search.domains.embeddables.traces.dates.mappers.transformers;

import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import org.springframework.stereotype.Component;

@Component
public class TraceDatesTransformer extends AbstractBaseTupleTransformer<TraceDatesEntity> {

  protected TraceDatesTransformer(IEntityMetadata<TraceDatesEntity> entityMetadata) {
    super(entityMetadata);
  }

}
