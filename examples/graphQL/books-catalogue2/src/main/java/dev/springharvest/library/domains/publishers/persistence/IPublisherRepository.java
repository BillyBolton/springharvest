package dev.springharvest.library.domains.publishers.persistence;

import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPublisherRepository extends JpaRepository<PublisherEntity, UUID> {
}
