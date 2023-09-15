package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IBookRepository extends IBaseCrudRepository<BookEntity, UUID> {}
