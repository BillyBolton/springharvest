package dev.springharvest.library.domains.locations.service;

import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.library.domains.locations.models.entities.LocationEntity;
import dev.springharvest.library.domains.locations.persistence.ILocationCrudRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationCrudService extends AbstractCrudService<LocationEntity, Long> {

  @Autowired
  protected LocationCrudService(ILocationCrudRepository baseRepository) {
    super(baseRepository);
  }

  public List<LocationEntity> findByProximity(Pageable page, double longitude, double latitude, double distance, boolean usePosGis) {
    return usePosGis ? ((ILocationCrudRepository) crudRepository).findProximity(page, longitude, latitude, distance)
                     : ((ILocationCrudRepository) crudRepository).findProximityFromDistanceTable(page, longitude, latitude, distance);
  }

}
