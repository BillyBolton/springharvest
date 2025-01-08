package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.persistence.IPublisherCrudRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublisherCrudService extends AbstractCrudService<PublisherEntity, UUID> {

  @Autowired
  protected PublisherCrudService(IPublisherCrudRepository baseRepository) {
    super(baseRepository);
  }
}
