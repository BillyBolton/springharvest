package dev.springharvest.library.domains.publishers.controllers.graphql;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.services.BookCrudService;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.services.PublisherCrudService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class PublisherGraphQLController {

    private final PublisherCrudService baseService;
    @Autowired
    protected PublisherGraphQLController(PublisherCrudService baseService) {
        this.baseService = baseService;
    }

    @QueryMapping()
    List<PublisherEntity> books() {
        //TODO: Fix
        return baseService.findAll(Pageable.unpaged()).getContent();
    }

    @QueryMapping()
    Optional<PublisherEntity> publisherById(@Argument UUID id) { return baseService.findById(id); }

}
