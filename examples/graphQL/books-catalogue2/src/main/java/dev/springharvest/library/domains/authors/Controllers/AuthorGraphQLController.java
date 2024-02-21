package dev.springharvest.library.domains.authors.Controllers;

import dev.springharvest.library.domains.authors.Services.AuthorService;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorGraphQLController {
    private final AuthorService authorService;

    @Autowired
    public AuthorGraphQLController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    List<AuthorEntity> authors()
    {
        return authorService.getAuthors();
    }
}
