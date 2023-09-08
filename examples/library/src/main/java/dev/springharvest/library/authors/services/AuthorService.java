package dev.springharvest.library.authors.services;

import dev.springharvest.crud.service.AbstractBaseCrudService;
import dev.springharvest.library.authors.mappers.IAuthorMapper;
import dev.springharvest.library.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.authors.models.entities.AuthorEntity;
import dev.springharvest.library.authors.persistence.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends AbstractBaseCrudService<AuthorDTO, AuthorEntity, Long> {
    @Autowired
    protected AuthorService(IAuthorMapper baseMapper, IAuthorRepository baseRepository) {
        super(baseMapper, baseRepository);
    }
}
