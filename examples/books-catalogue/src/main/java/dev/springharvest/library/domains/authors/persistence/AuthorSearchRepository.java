package dev.springharvest.library.domains.authors.persistence;

import dev.springharvest.library.domains.authors.mappers.search.tuples.AuthorRootTupleTransformer;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDao;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorSearchRepository extends AbstractCriteriaSearchDao<AuthorEntity, UUID, AuthorFilterRequestBO> {

  AuthorSearchRepository(AuthorRootTupleTransformer tupleTransformer, AuthorEntityMetadata entityMetadata) {
    super(entityMetadata.getDomainName(), tupleTransformer);
  }

}
