package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublisherCrudRepository extends IBaseCrudRepository<PublisherEntity, UUID> {

}
