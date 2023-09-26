package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.crud.persistence.ICrudRepository;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookCrudRepository extends ICrudRepository<BookEntity, UUID> {

}
