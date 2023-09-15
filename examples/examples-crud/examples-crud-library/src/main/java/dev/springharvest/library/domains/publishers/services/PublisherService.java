package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.publishers.persistence.IPublisherRepository;
import dev.springharvest.shared.domains.publishers.mappers.IPublisherMapper;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublisherService extends AbstractBaseCrudService<PublisherDTO, PublisherEntity, UUID> {

    @Autowired
    protected PublisherService(IPublisherMapper baseMapper, IPublisherRepository baseRepository) {
        super(baseMapper, baseRepository);
    }

}
