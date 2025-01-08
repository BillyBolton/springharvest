package dev.springharvest.library.domains.pet.mappers.search.tuples;

import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.library.domains.pet.models.entities.PetEntityMetadata;
import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

@Slf4j
@Component
public class PetRootTupleTransformer extends AbstractBaseTupleTransformer<PetEntity> {

    @Autowired
    public PetRootTupleTransformer(PetEntityMetadata entityMetadata) {
        super(entityMetadata);
    }

}
