package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends IBaseCrudRepository<BookEntity, UUID> {

}
