package dev.springharvest.library.domains.locations.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.locations.models.entities.LocationEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationCrudRepository extends ICrudRepository<LocationEntity, Long> {

  @Query("SELECT l FROM LocationEntity l " +
         "WHERE ST_Distance(l.point, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) < :distance")
  List<LocationEntity> findProximity(Pageable page,
                                     @Param("longitude") double longitude,
                                     @Param("latitude") double latitude,
                                     @Param("distance") double distance);

  @Query("SELECT l " +
         "FROM LocationEntity l " +
         "JOIN DistanceEntity d ON l.point IN (d.sourcePoint, d.targetPoint) " +
         "WHERE (d.sourcePoint = ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) " +
         "OR d.targetPoint = ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) " +
         "   AND d.kmDistance < :distance")
  List<LocationEntity> findProximityFromDistanceTable(Pageable page,
                                                      @Param("longitude") double longitude,
                                                      @Param("latitude") double latitude,
                                                      @Param("distance") double distance);
}
