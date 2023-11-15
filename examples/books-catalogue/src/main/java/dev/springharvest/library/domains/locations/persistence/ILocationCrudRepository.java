package dev.springharvest.library.domains.locations.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.locations.models.entities.LocationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationCrudRepository extends ICrudRepository<LocationEntity, Long> {

  @Query("SELECT l FROM LocationEntity l WHERE ST_Distance(l.point, ST_MakePoint(:longitude, :latitude)) < :distance")
  List<LocationEntity> findProximity(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("distance") double distance);


}
