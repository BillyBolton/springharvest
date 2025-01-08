package dev.springharvest.library.domains.pet.persistence;

import dev.springharvest.library.domains.pet.mappers.search.tuples.PetTupleTransformer;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.library.domains.pet.models.entities.PetEntityMetadata;
import dev.springharvest.library.domains.pet.models.queries.PetFilterRequestBO;
import dev.springharvest.search.domains.base.persistence.AbstractCriteriaSearchDao;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PetSearchRepository extends AbstractCriteriaSearchDao<PetEntity, UUID, PetFilterRequestBO> {

    PetSearchRepository(PetTupleTransformer tupleTransformer, PetEntityMetadata entityMetadata) {
        super(entityMetadata.getDomainName(), tupleTransformer);
    }
}
