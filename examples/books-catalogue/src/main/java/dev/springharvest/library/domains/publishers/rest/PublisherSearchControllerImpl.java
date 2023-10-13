package dev.springharvest.library.domains.publishers.rest;

import dev.springharvest.library.domains.publishers.constants.PublisherConstants;
import dev.springharvest.library.domains.publishers.mappers.IPublisherMapper;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.domains.publishers.services.PublisherSearchService;
import dev.springharvest.search.domains.base.rest.AbstractSearchController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = PublisherConstants.Controller.TAG)
@RequestMapping(PublisherConstants.Controller.DOMAIN_CONTEXT)
public class PublisherSearchControllerImpl
    extends AbstractSearchController<PublisherDTO, PublisherEntity, UUID, PublisherFilterRequestDTO,
    PublisherFilterRequestBO, PublisherFilterDTO, PublisherFilterBO> {

  protected PublisherSearchControllerImpl(IPublisherMapper publisherMapper, PublisherSearchService baseService) {
    super(publisherMapper, baseService);
  }

}
