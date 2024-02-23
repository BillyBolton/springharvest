package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.persistence;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IAuthorRepository extends JpaRepository<AuthorEntity, UUID> {

}
