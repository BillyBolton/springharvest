package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublisherCrudRepository extends ICrudRepository<PublisherEntity, UUID> {}
