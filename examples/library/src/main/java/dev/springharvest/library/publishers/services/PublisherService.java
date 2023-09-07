package dev.springharvest.library.publishers.services;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.publishers.mappers.IPublisherMapper;
import dev.springharvest.library.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.publishers.models.entities.PublisherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService extends AbstractBaseCrudService<PublisherDTO, PublisherEntity, Long> {

    @Autowired
    protected PublisherService(IPublisherMapper baseMapper,
                               IBaseCrudRepository<PublisherEntity, Long> baseRepository) {
        super(baseMapper, baseRepository);
    }

}
