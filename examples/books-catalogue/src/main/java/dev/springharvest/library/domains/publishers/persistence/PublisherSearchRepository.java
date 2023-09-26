package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.library.domains.publishers.mappers.search.tuples.PublisherRootTupleTransformer;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDao;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class PublisherSearchRepository
    extends AbstractCriteriaSearchDao<PublisherEntity, UUID, PublisherFilterRequestBO> {

  PublisherSearchRepository(PublisherRootTupleTransformer tupleTransformer, PublisherEntityMetadata entityMetadata) {
    super(entityMetadata.getDomainName(), tupleTransformer);
  }

}

