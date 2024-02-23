package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.persistence;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.models.entities.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPublisherRepository extends JpaRepository<PublisherEntity, UUID> {

}
