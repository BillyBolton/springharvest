package graphqlJavaTutorial2.simpleGraphqlDemo2.domains;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.services.AuthorService;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.models.entities.BookEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
class AuthorBookController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public AuthorBookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @QueryMapping
    public List<BookEntity> allBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public List<AuthorEntity> allAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public BookEntity bookById(@Argument UUID id) {
        return bookService.getById(id);
    }

    @QueryMapping
    public AuthorEntity authorById(@Argument UUID id) {
        return authorService.getById(id);
    }

//    @SchemaMapping
//    public AuthorEntity author(BookEntity book) {
//        // Simplement retourner l'auteur directement depuis l'entité livre
//        return book.getAuthor();
//    }
}

