package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.persistence;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IBookRepository extends JpaRepository<BookEntity, UUID> {

}