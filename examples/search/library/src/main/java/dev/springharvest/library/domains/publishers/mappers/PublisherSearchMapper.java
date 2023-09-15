package dev.springharvest.library.domains.publishers.mappers;


import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.global.GlobalClazzResolver;
import dev.springharvest.search.mapper.queries.ISearchMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class PublisherSearchMapper
        implements ISearchMapper<PublisherFilterRequestDTO, PublisherFilterRequestBO, PublisherFilterDTO, PublisherFilterBO> {

    @Autowired
    private GlobalClazzResolver globalClazzResolver;

    @Autowired
    private PublisherEntityMetadata entityMetadata;


    public Class<?> getClazz(String path) {
        return globalClazzResolver.getClazz(path);
    }

    public Set<String> getRoots() {
        return entityMetadata.getRootPaths();
    }

}

