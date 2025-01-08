package dev.springharvest.library.domains.pet.mappers.search.tuples;

import dev.springharvest.library.domains.authors.mappers.search.tuples.AuthorRootTupleTransformer;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.library.domains.pet.models.entities.PetEntityMetadata;
import dev.springharvest.search.domains.embeddables.traces.trace.mappers.transformers.UUIDTraceDataTransformer;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetTupleTransformer extends PetRootTupleTransformer{

    private final UUIDTraceDataTransformer traceDataTransformer;

    @Autowired
    public PetTupleTransformer(PetEntityMetadata entityMetadata,
                                UUIDTraceDataTransformer traceDataTransformer) {
        super(entityMetadata);
        this.traceDataTransformer = traceDataTransformer;
    }

    @Override
    public void upsertAssociatedEntities(PetEntity entity, Tuple tuple) {
        var traceData = traceDataTransformer.apply(tuple);
        if (traceData != null) {
            entity.setTraceData(traceData.isEmpty() ? null : traceData);
        }
    }
}
