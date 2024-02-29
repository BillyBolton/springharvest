package dev.springharvest.library.domains.books.controllers;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.awt.print.Book;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class BookGraphQLController {
    private final BookService bookService;

    @QueryMapping
    public List<BookEntity> books(){ return bookService.getBooks(); }

    @QueryMapping()
    public BookEntity bookById(@Argument UUID id){ return  bookService.getBookById(id); }

    @QueryMapping()
    public List<BookEntity> booksByAuthor(@Argument UUID id) { return  bookService.getBooksByAuthor(id); }

    @QueryMapping()
    public List<BookEntity> booksByPublisher(@Argument UUID id) { return  bookService.getBooksByPublisher(id); }
}
