package dev.springharvest.library.domains.authors.mappers;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestBO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.library.global.GlobalClazzResolver;
import dev.springharvest.search.mapper.queries.ISearchMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class IAuthorSearchMapper
        implements ISearchMapper<AuthorFilterRequestDTO, AuthorFilterRequestBO, AuthorFilterDTO, AuthorFilterBO> {

    @Autowired
    private GlobalClazzResolver globalClazzResolver;

    @Autowired
    private AuthorEntityMetadata entityMetadata;


    public Class<?> getClazz(String path) {
        return globalClazzResolver.getClazz(path);
    }

    public Set<String> getRoots() {
        return entityMetadata.getRootPaths();
    }

}
