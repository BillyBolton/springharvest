package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPublisherRepository extends IBaseCrudRepository<PublisherEntity, UUID> {}
