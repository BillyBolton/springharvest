package dev.springharvest.library.domains.authors.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.persistence.IAuthorCrudRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorCrudService extends AbstractBaseCrudService<AuthorEntity, UUID> {

  @Autowired
  protected AuthorCrudService(IAuthorCrudRepository baseRepository) {
    super(baseRepository);
  }

}
