package dev.springharvest.library.domains.publishers.controllers.rest;

import dev.springharvest.crud.domains.base.rest.AbstractCrudController;
import dev.springharvest.library.domains.publishers.constants.PublisherConstants;
import dev.springharvest.library.domains.publishers.mappers.IPublisherMapper;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.services.PublisherCrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = PublisherConstants.Controller.TAG)
@RequestMapping(PublisherConstants.Controller.DOMAIN_CONTEXT)
public class PublisherCrudControllerImpl extends AbstractCrudController<PublisherDTO, PublisherEntity, UUID> {

  protected PublisherCrudControllerImpl(IPublisherMapper baseModelMapper, PublisherCrudService baseService) {
    super(baseModelMapper, baseService);
  }

}
