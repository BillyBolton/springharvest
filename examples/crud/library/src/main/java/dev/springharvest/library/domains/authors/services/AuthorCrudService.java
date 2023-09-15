package dev.springharvest.library.domains.authors.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.persistence.IAuthorRepository;
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
