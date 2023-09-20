package dev.springharvest.shared.domains.books.mappers;

import dev.springharvest.crud.mappers.CyclicMappingHandler;
import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import dev.springharvest.shared.domains.publishers.mappers.IPublisherMapper;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        uses = {IAuthorMapper.class, IPublisherMapper.class})
public interface IBookMapper extends IBaseModelMapper<BookDTO, BookEntity, UUID> {

  @Override
  @Mapping(target = "author", source = ".")
  @Mapping(target = "publisher", source = ".")
  BookDTO toDto(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "author", source = ".")
  @Mapping(target = "publisher", source = ".")
  BookEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
