package dev.springharvest.library.domains.publishers.controllers;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class PublisherGraphQLController {
    private final PublisherService publisherService;

    @QueryMapping
    public List<PublisherEntity> publishers(){ return publisherService.getPublishers(); }

    @QueryMapping()
    PublisherEntity publisherById(@Argument UUID id) { return publisherService.getPublisher(id); }
}
