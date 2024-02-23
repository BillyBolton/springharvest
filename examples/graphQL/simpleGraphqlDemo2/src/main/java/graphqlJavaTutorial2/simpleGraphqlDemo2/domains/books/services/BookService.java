package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.services;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.models.entities.BookEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.persistence.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookService {
    private final IBookRepository bookRepository;

    // Get all books
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by ID
    public BookEntity getById(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }
}
