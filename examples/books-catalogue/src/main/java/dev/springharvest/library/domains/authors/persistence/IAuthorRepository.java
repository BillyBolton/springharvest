package dev.springharvest.library.domains.authors.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends IBaseCrudRepository<AuthorEntity, UUID> {

}
