package dev.springharvest.library.domains.books.services;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.persistence.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookService {
    private final IBookRepository booksRepository;

    public List<BookEntity> getBooks(){ return booksRepository.findAll(); }

    public BookEntity getBookById(UUID id) {
        return booksRepository.existsById(id) ? booksRepository.getReferenceById(id)
                                                : null;
    }

    public List<BookEntity> getBooksByAuthor(UUID id)
    {
        return booksRepository.findByAuthorId(id);
    }

    public List<BookEntity> getBooksByPublisher(UUID id)
    {
        return  booksRepository.findByPublisherId(id);
    }
}
