package dev.springharvest.library.domains.locations.service;

import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.library.domains.locations.models.entities.LocationEntity;
import dev.springharvest.library.domains.locations.persistence.ILocationCrudRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationCrudService extends AbstractCrudService<LocationEntity, Long> {

  @Autowired
  protected LocationCrudService(ILocationCrudRepository baseRepository) {
    super(baseRepository);
  }

  public List<LocationEntity> findByProximity(double longitude, double latitude, double distance) {
    return ((ILocationCrudRepository) crudRepository).findProximity(longitude, latitude, distance);
  }

}
