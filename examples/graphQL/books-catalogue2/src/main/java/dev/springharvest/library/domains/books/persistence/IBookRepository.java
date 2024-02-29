package dev.springharvest.library.domains.books.persistence;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IBookRepository extends JpaRepository<BookEntity, UUID> {
    @Query(value = "SELECT * FROM Books B WHERE B.author_id = ?1", nativeQuery = true)
    public List<BookEntity> findByAuthorId(UUID id);
    @Query(value = "SELECT * FROM Books B WHERE B.publisher_id = ?1", nativeQuery = true)
    public List<BookEntity> findByPublisherId(UUID id);
}
