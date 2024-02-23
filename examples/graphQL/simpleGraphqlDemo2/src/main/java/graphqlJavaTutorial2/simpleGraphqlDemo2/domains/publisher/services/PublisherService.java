package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.services;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.persistence.IAuthorRepository;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.models.entities.PublisherEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.persistence.IPublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PublisherService {
    private final IPublisherRepository publisherRepository;

    // Find all publishers
    public List<PublisherEntity> getAllPublisher() {
        return publisherRepository.findAll();
    }

    // Find a publisher by ID
    public PublisherEntity getById(UUID id) {
        return publisherRepository.findById(id).orElse(null);
    }
}
