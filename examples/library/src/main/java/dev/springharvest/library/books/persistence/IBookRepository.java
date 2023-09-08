package dev.springharvest.library.books.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.library.books.models.entities.BookEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends IBaseCrudRepository<BookEntity, Long> {
}
