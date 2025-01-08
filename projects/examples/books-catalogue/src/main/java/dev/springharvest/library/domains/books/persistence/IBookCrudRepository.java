package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookCrudRepository extends ICrudRepository<BookEntity, UUID> {

}
