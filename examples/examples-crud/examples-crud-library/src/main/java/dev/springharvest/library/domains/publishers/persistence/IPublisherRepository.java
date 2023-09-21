package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublisherRepository extends IBaseCrudRepository<PublisherEntity, UUID> {

}
