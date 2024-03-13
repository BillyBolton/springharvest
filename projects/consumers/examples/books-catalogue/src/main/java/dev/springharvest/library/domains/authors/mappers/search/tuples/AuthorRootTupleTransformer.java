package dev.springharvest.library.domains.authors.mappers.search.tuples;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.search.domains.embeddables.traces.trace.mappers.transformers.UUIDTraceDataTransformer;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorRootTupleTransformer extends AbstractBaseTupleTransformer<AuthorEntity> {

  private final UUIDTraceDataTransformer traceDataTransformer;

  @Autowired
  public AuthorRootTupleTransformer(AuthorEntityMetadata entityMetadata, UUIDTraceDataTransformer traceDataTransformer) {
    super(entityMetadata);
    this.traceDataTransformer = traceDataTransformer;
  }

  @Override
  public void upsertAssociatedEntities(AuthorEntity entity, Tuple tuple) {
    var traceData = traceDataTransformer.apply(tuple);
    if (ObjectUtils.isNotEmpty(traceData) && !traceData.isEmpty()) {
      entity.setTraceData(traceData);
    }
  }

}
