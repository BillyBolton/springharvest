package dev.springharvest.search.domains.embeddables.traces.users.mappers.transformers;

import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.UUIDTraceUsersEntity;
import org.springframework.stereotype.Component;

@Component
public class UUIDTraceUsersTransformer extends AbstractBaseTupleTransformer<UUIDTraceUsersEntity> {

  protected UUIDTraceUsersTransformer(IEntityMetadata<UUIDTraceUsersEntity> entityMetadata) {
    super(entityMetadata);
  }

}
