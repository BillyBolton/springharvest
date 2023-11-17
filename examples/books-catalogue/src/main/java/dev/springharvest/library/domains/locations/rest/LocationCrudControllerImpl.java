package dev.springharvest.library.domains.locations.rest;

import dev.springharvest.crud.domains.base.rest.AbstractCrudController;
import dev.springharvest.library.domains.locations.constants.LocationConstants;
import dev.springharvest.library.domains.locations.mappers.ILocationMapper;
import dev.springharvest.library.domains.locations.models.dtos.LocationDTO;
import dev.springharvest.library.domains.locations.models.entities.LocationEntity;
import dev.springharvest.library.domains.locations.service.LocationCrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@Tag(name = LocationConstants.Controller.TAG)
@RequestMapping(LocationConstants.Controller.DOMAIN_CONTEXT)
public class LocationCrudControllerImpl extends AbstractCrudController<LocationDTO, LocationEntity, Long> {

  protected LocationCrudControllerImpl(ILocationMapper baseModelMapper, LocationCrudService baseService) {
    super(baseModelMapper, baseService);
  }

  @GetMapping(value = LocationConstants.Controller.FIND_BY_PROXIMITY,
              produces = MediaType.APPLICATION_JSON_VALUE)
  public List<LocationDTO> findByProximity(@RequestParam(name = "longitude", required = true) double longitude,
                                           @RequestParam(name = "latitude", required = true) double latitude,
                                           @RequestParam(name = "distance", required = true) double distance,
                                           @RequestParam(name = "usePosGis", required = false, defaultValue = "false") boolean usePosGis,
                                           @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                           @RequestParam(name = "pageSize", required = false) Integer pageSize) {

    if (pageNumber == null ^ pageSize == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If either pageNumber or pageSize is provided, both must be provided");
    }

    if (pageNumber != null && pageNumber < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number must be greater than or equal to 0");
    }

    if (pageSize != null && pageSize < 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page size must be greater than 0");
    }

    boolean isPaged = pageNumber != null && pageSize != null;
    List<LocationEntity> entities = ((LocationCrudService) crudService).findByProximity(isPaged ? PageRequest.of(pageNumber, pageSize) : Pageable.unpaged(),
                                                                                        longitude,
                                                                                        latitude,
                                                                                        distance,
                                                                                        usePosGis);
    if (CollectionUtils.isEmpty(entities)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No locations found");
    }
    return super.modelMapper.entityToDto(entities);
  }

}
