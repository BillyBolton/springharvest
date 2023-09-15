package dev.springharvest.library.domains.publishers.rest;

import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.domains.publishers.services.PublisherSearchService;
import dev.springharvest.search.rest.AbstractBaseSearchController;
import dev.springharvest.shared.domains.publishers.constants.PublisherConstants;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@Tag(name = PublisherConstants.Controller.TAG)
@RequestMapping(PublisherConstants.Controller.DOMAIN_CONTEXT)
public class PublisherSearchControllerImpl
        extends AbstractBaseSearchController<PublisherDTO, PublisherEntity, UUID, PublisherFilterRequestDTO, PublisherFilterRequestBO, PublisherFilterDTO, PublisherFilterBO> {

    protected PublisherSearchControllerImpl(PublisherSearchService baseService) {
        super(baseService);
    }

}
