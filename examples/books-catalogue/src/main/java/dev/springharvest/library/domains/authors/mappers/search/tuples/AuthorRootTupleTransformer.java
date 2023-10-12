package dev.springharvest.library.domains.authors.mappers.search.tuples;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.search.domains.base.mappers.transformers.AbstractBaseTupleTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorRootTupleTransformer extends AbstractBaseTupleTransformer<AuthorEntity> {

  @Autowired
  public AuthorRootTupleTransformer(AuthorEntityMetadata entityMetadata) {
    super(entityMetadata);
  }

}
