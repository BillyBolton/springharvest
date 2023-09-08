package dev.springharvest.library.books.mappers;

import dev.springharvest.crud.mappers.CyclicMappingHandler;
import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.library.authors.mappers.IAuthorMapper;
import dev.springharvest.library.books.models.dtos.BookDTO;
import dev.springharvest.library.books.models.entities.BookEntity;
import dev.springharvest.library.publishers.mappers.IPublisherMapper;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {IAuthorMapper.class, IPublisherMapper.class})
public interface IBookMapper extends IBaseModelMapper<BookDTO, BookEntity, Long> {

    @Override
    @Mapping(target = "author", source = ".")
    @Mapping(target = "publisher", source = ".")
    BookDTO toDto(Map<String, String> source, @Context CyclicMappingHandler context);

    @Override
    @Mapping(target = "author", source = ".")
    @Mapping(target = "publisher", source = ".")
    BookEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
