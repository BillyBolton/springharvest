package dev.springharvest.library.domains.authors.persistence;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IAuthorRepository extends JpaRepository<AuthorEntity, UUID> {

}
