package dev.springharvest.library.domains.publishers.services;

import dev.springharvest.library.domains.publishers.mappers.search.PublisherSearchMapper;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.domains.publishers.persistence.PublisherSearchRepository;
import dev.springharvest.search.service.AbstractSearchService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherSearchService
    extends AbstractSearchService<PublisherEntity, UUID, PublisherFilterRequestDTO, PublisherFilterRequestBO,
    PublisherFilterDTO, PublisherFilterBO> {

  @Autowired
  protected PublisherSearchService(PublisherSearchMapper filterMapper, PublisherSearchRepository searchRepository) {
    super(filterMapper, searchRepository);
  }

}
