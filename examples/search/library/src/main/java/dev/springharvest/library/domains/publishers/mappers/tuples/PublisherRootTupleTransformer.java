package dev.springharvest.library.domains.publishers.mappers.tuples;


import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.search.mapper.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PublisherRootTupleTransformer extends AbstractBaseTupleTransformer<PublisherEntity> {

    @Autowired
    public PublisherRootTupleTransformer(PublisherEntityMetadata entityMetadata) {
        super(entityMetadata);
    }

    @Override
    protected PublisherEntity getNewEntity() {
        return PublisherEntity.builder().build();
    }

    @Override
    protected void mapTupleElement(PublisherEntity entity, String alias, Object value) {
        switch (alias) {
            case PublisherEntityMetadata.Paths.PUBLISHER_ID -> entity.setId((UUID) value);
            case PublisherEntityMetadata.Paths.PUBLISHER_NAME -> entity.setName((String) value);
            default -> {
                // continue
            }
        }
    }

    @Override
    public void upsertAssociatedEntities(PublisherEntity entity, Tuple tuple) {
    }

}
