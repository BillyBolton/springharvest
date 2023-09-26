package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.crud.persistence.ICrudRepository;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublisherCrudRepository extends ICrudRepository<PublisherEntity, UUID> {

}
