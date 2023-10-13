package dev.springharvest.library.domains.publishers.mappers.search.tuples;


import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.search.domains.embeddables.traces.trace.mappers.transformers.UUIDTraceDataTransformer;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublisherRootTupleTransformer extends AbstractBaseTupleTransformer<PublisherEntity> {

  private final UUIDTraceDataTransformer traceDataTransformer;

  @Autowired
  public PublisherRootTupleTransformer(PublisherEntityMetadata entityMetadata,
                                       UUIDTraceDataTransformer traceDataTransformer) {
    super(entityMetadata);
    this.traceDataTransformer = traceDataTransformer;
  }

  @Override
  public void upsertAssociatedEntities(PublisherEntity entity, Tuple tuple) {
    var traceData = traceDataTransformer.apply(tuple);
    if (ObjectUtils.isNotEmpty(traceData) && !traceData.isEmpty()) {
      entity.setTraceData(traceData);
    }
  }

}
