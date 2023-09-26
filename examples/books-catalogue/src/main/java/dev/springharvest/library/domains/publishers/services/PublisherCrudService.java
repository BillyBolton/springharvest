package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.persistence.IPublisherCrudRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherCrudService extends AbstractBaseCrudService<PublisherEntity, UUID> {

  @Autowired
  protected PublisherCrudService(IPublisherCrudRepository baseRepository) {
    super(baseRepository);
  }

}
