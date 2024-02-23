package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.services;

import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.persistence.IAuthorRepository;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final IAuthorRepository authorRepository;

    // Retourne tous les auteurs
    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Trouve un auteur par son ID
    public AuthorEntity getById(UUID id) {
        return authorRepository.findById(id).orElse(null);
    }
}
