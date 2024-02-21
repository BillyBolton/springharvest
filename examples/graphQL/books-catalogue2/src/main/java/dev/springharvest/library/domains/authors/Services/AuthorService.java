package dev.springharvest.library.domains.authors.Services;

import dev.springharvest.library.domains.authors.Persistence.IAuthorRepository;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final IAuthorRepository authorRepository;
    public List<AuthorEntity> getAuthors()
    {
        return authorRepository.findAll();
    }
}
