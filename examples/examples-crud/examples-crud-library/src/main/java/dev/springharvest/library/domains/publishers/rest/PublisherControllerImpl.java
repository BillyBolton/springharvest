package dev.springharvest.library.domains.publishers.rest;

import dev.springharvest.crud.rest.AbstractBaseCrudController;
import dev.springharvest.library.domains.publishers.services.PublisherService;
import dev.springharvest.shared.domains.publishers.constants.PublisherConstants;
import dev.springharvest.shared.domains.publishers.mappers.IPublisherMapper;
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
public class PublisherControllerImpl extends AbstractBaseCrudController<PublisherDTO, PublisherEntity, UUID> {

    protected PublisherControllerImpl(IPublisherMapper baseModelMapper, PublisherService baseService) {
        super(baseModelMapper, baseService);
    }

}
