package dev.springharvest.library.domains.authors.services;

import dev.springharvest.library.domains.authors.persistence.IAuthorRepository;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final IAuthorRepository authorRepository;
    public List<AuthorEntity> getAuthors()
    {
        return authorRepository.findAll();
    }

    public AuthorEntity getAuthor(UUID id) { return authorRepository.existsById(id) ? authorRepository.getReferenceById(id) : null; }
}
