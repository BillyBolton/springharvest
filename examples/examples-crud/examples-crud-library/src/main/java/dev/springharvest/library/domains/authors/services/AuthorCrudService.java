package dev.springharvest.library.domains.authors.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.authors.persistence.IAuthorRepository;
import dev.springharvest.shared.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorCrudService extends AbstractBaseCrudService<AuthorDTO, AuthorEntity, UUID> {

    @Autowired
    protected AuthorCrudService(IAuthorMapper baseMapper, IAuthorRepository baseRepository) {
        super(baseMapper, baseRepository);
    }

}
