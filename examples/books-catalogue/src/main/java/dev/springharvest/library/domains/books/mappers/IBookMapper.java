package dev.springharvest.library.domains.books.mappers;

import dev.springharvest.library.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.library.domains.books.models.dtos.BookDTO;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.publishers.mappers.IPublisherMapper;
import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.embeddables.traces.trace.mappers.UUIDTraceDataMapper;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        uses = {IAuthorMapper.class, IPublisherMapper.class, UUIDTraceDataMapper.class})
public interface IBookMapper extends IBaseModelMapper<BookDTO, BookEntity, UUID> {

  @Override
  @Mapping(target = "author", source = ".")
  @Mapping(target = "publisher", source = ".")
  @Mapping(target = "traceData", source = ".")
  BookDTO toDto(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "author", source = ".")
  @Mapping(target = "publisher", source = ".")
  @Mapping(target = "traceData", source = ".")
  BookEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
