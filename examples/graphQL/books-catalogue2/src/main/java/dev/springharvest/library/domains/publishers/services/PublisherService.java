package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.persistence.IPublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PublisherService {
    private final IPublisherRepository publisherRepository;

    public List<PublisherEntity> getPublishers() { return publisherRepository.findAll(); }

    public PublisherEntity getPublisher(UUID id) { return  publisherRepository.existsById(id) ? publisherRepository.getReferenceById(id) : null; }
}
