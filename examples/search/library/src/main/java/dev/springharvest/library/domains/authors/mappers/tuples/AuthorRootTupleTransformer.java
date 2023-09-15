package dev.springharvest.library.domains.authors.mappers.tuples;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.search.mapper.transformers.AbstractBaseTupleTransformer;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AuthorRootTupleTransformer extends AbstractBaseTupleTransformer<AuthorEntity> {

    @Autowired
    public AuthorRootTupleTransformer(AuthorEntityMetadata entityMetadata) {
        super(entityMetadata);
    }

    @Override
    protected AuthorEntity getNewEntity() {
        return AuthorEntity.builder().build();
    }

    @Override
    protected void mapTupleElement(AuthorEntity entity, String alias, Object value) {
        switch (alias) {
            case AuthorEntityMetadata.Paths.AUTHOR_ID -> entity.setId((UUID) value);
            case AuthorEntityMetadata.Paths.AUTHOR_NAME -> entity.setName((String) value);
            default -> {
                // continue
            }
        }
    }

    @Override
    public void upsertAssociatedEntities(AuthorEntity entity, Tuple tuple) {
    }

}
