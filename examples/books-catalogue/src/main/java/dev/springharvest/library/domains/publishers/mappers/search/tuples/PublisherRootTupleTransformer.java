package dev.springharvest.library.domains.publishers.mappers.search.tuples;


import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.search.mappers.transformers.AbstractBaseTupleTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublisherRootTupleTransformer extends AbstractBaseTupleTransformer<PublisherEntity> {

  @Autowired
  public PublisherRootTupleTransformer(PublisherEntityMetadata entityMetadata) {
    super(entityMetadata);
  }

}
