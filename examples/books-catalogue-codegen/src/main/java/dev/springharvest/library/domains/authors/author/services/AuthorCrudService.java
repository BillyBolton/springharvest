package dev.springharvest.library.domains.authors.author.services;

import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.library.domains.authors.author.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.author.persistence.IAuthorCrudRepository_;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorCrudService extends AbstractCrudService<AuthorEntity, UUID> {

  @Autowired
  protected AuthorCrudService(IAuthorCrudRepository_ baseRepository) {
    super(baseRepository);
  }

}