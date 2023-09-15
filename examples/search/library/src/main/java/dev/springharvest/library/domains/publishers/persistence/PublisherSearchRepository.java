package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.library.domains.publishers.mappers.tuples.PublisherRootTupleTransformer;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDaoImpl;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PublisherSearchRepository
        extends AbstractCriteriaSearchDaoImpl<PublisherEntity, UUID, PublisherFilterRequestBO> {

    PublisherSearchRepository(PublisherRootTupleTransformer tupleTransformer, PublisherEntityMetadata entityMetadata) {
        super(entityMetadata.getDomainName(), tupleTransformer);
    }

}

