package dev.springharvest.library.domains.locations.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.locations.models.entities.DistanceEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IDistanceCrudRepository extends ICrudRepository<DistanceEntity, UUID> {

}
