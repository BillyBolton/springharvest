package dev.springharvest.library.domains.locations.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.springharvest.library.domains.locations.models.dtos.LocationDTO;
import dev.springharvest.library.domains.locations.models.entities.LocationEntity;
import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ILocationMapper extends IBaseModelMapper<LocationDTO, LocationEntity, Long> {

  @Override
  @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "county", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "province", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "provinceCode", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "postalCodeArea", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "type", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "mapReference", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "latitude", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "longitude", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "censusDivision", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "areaCode", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "timeZone", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  LocationDTO setDirtyFields(LocationDTO source, @MappingTarget LocationDTO target, @Context CyclicMappingHandler context);

  default Point convertStringToPoint(String pointString) {
    // Create ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Read the JSON string and convert it to a Point object
      WKTReader wktReader = new WKTReader(new GeometryFactory());
      return (Point) wktReader.read(objectMapper.readValue(pointString, org.locationtech.jts.geom.Geometry.class).toText());
    } catch (ParseException | java.io.IOException e) {
      throw new RuntimeException("Error converting String to Point", e);
    }
  }

  default String pointToString(Point point) {
    return point.toText();
  }

}

