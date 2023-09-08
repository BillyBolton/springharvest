package dev.springharvest.library.publishers.mappers;

import dev.springharvest.crud.mappers.CyclicMappingHandler;
import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.library.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.publishers.models.entities.PublisherEntity;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface IPublisherMapper extends IBaseModelMapper<PublisherDTO, PublisherEntity, UUID> {

    @Override
    PublisherDTO entityToDto(PublisherEntity entity);

    @Override
    PublisherEntity dtoToEntity(PublisherDTO dto);

    @Override
    @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    PublisherDTO setDirtyFields(PublisherDTO source, @MappingTarget PublisherDTO target, @Context CyclicMappingHandler context);

}
