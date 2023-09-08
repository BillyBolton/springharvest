package dev.springharvest.library.authors.mappers;

import dev.springharvest.crud.mappers.CyclicMappingHandler;
import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.library.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.authors.models.entities.AuthorEntity;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface IAuthorMapper extends IBaseModelMapper<AuthorDTO, AuthorEntity, Long> {

    @Override
    AuthorDTO entityToDto(AuthorEntity entity);

    @Override
    AuthorEntity dtoToEntity(AuthorDTO dto);

    @Override
    @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    AuthorDTO setDirtyFields(AuthorDTO source, @MappingTarget AuthorDTO target, @Context CyclicMappingHandler context);

}
