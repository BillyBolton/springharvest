package dev.springharvest.library.global;

/*import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.services.AuthorService;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.models.entities.BookEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.services.BookService;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.models.entities.PublisherEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.services.PublisherService;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
class GlobalResolver {
    /*private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Autowired
    public GlobalResolver(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

// Queries for Books
    @QueryMapping
    public List<BookEntity> allBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public BookEntity bookById(@Argument UUID id) {
        return bookService.getById(id);
    }

// Queries for Authors
    @QueryMapping
    public List<AuthorEntity> allAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public AuthorEntity authorById(@Argument UUID id) {
        return authorService.getById(id);
    }

    @SchemaMapping
    public AuthorEntity author(BookEntity book) {
        return book.getAuthor(); //Get the author directly from BookEntity
    }

// Queries for Publishers
    @QueryMapping
    public List<PublisherEntity> allPublishers() {
        return publisherService.getAllPublisher();
    }

    @QueryMapping
    public PublisherEntity publisherById(@Argument UUID id) {
        return publisherService.getById(id);
    }
*/
}

