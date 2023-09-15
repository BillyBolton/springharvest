package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.publishers.persistence.IPublisherRepository;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublisherService extends AbstractBaseCrudService<PublisherEntity, UUID> {

    @Autowired
    protected PublisherService(IPublisherRepository baseRepository) {
        super(baseRepository);
    }

}
