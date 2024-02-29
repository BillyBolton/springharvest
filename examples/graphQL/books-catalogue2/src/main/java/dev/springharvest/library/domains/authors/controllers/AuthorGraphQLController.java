package dev.springharvest.library.domains.authors.controllers;

import dev.springharvest.library.domains.authors.services.AuthorService;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class AuthorGraphQLController {
    private final AuthorService authorService;

    @QueryMapping()
    List<AuthorEntity> authors()
    {
        return authorService.getAuthors();
    }

    @QueryMapping()
    AuthorEntity authorById(@Argument UUID id) { return authorService.getAuthor(id); }
}
