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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
  
  @GetMapping(value = "{lat}/{lon}/{distance}",
              produces = MediaType.APPLICATION_JSON_VALUE)
  public List<LocationDTO> findByProximity(@PathVariable double lat,
                                           @PathVariable double lon,
                                           @PathVariable double distance) {
    List<LocationEntity> entities = ((LocationCrudService) crudService).findByProximity(lon, lat, distance);
    if (CollectionUtils.isEmpty(entities)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No locations found");
    }
    return super.modelMapper.entityToDto(entities);
  }

}
