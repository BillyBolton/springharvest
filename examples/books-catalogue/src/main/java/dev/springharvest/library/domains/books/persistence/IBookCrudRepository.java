package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookCrudRepository extends ICrudRepository<BookEntity, UUID> {

  @Query(value = "SELECT * FROM Books B WHERE B.author_id = ?1", nativeQuery = true)
  List<BookEntity> findByAuthorId(UUID id);
  @Query(value = "SELECT * FROM Books B WHERE B.publisher_id = ?1", nativeQuery = true)
  List<BookEntity> findByPublisherId(UUID id);

}
