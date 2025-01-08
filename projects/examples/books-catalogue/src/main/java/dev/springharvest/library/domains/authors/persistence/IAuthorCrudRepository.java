package dev.springharvest.library.domains.authors.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorCrudRepository extends ICrudRepository<AuthorEntity, UUID> {

}
