package dev.springharvest.search.mappers.queries;

import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import java.lang.reflect.Field;
import java.util.Set;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

/**
 * This interface contracts the methods that are used to map the filter parameter objects.
 *
 * @param <RD> An object that extends the BaseFilterRequestDTO
 * @param <RB> An object that extends the BaseFilterRequestBO
 * @param <D>  An object that extends the BaseFilterDTO
 * @param <B>  An object that extends the BaseFilterBO
 * @see BaseFilterRequestDTO
 * @see BaseFilterRequestBO
 * @see BaseFilterDTO
 * @see BaseFilterBO
 * @see IParameterMapper
 */
public interface IFilterMapper<RD extends BaseFilterRequestDTO, RB extends BaseFilterRequestBO, D extends BaseFilterDTO, B extends BaseFilterBO>
    extends IParameterMapper {

  /**
   * This method is used to map a BaseFilterRequestDTO object to a BaseFilterRequestBO.
   *
   * @param source The BaseFilterRequestDTO object that will be mapped from.
   * @return The BaseFilterRequestBO object that will be mapped to.
   * @see BaseFilterRequestDTO
   * @see BaseFilterRequestBO
   */
  RB toBO(RD source);

  /**
   * This method is used to map a Set of BaseFilterRequestDTO objects to a Set of BaseFilterRequestBO.
   *
   * @param source The Set of BaseFilterRequestDTO objects that will be mapped from.
   * @return The Set of BaseFilterRequestBO objects that will be mapped to.
   * @see BaseFilterRequestDTO
   * @see BaseFilterRequestBO
   */
  Set<RB> toBO(Set<RD> source);

  /**
   * This method is used to map a BaseFilterRequestBO object to a BaseFilterRequestDTO.
   *
   * @param filterDTO The BaseFilterRequestBO object that will be mapped from.
   * @return The BaseFilterRequestDTO object that will be mapped to.
   * @see BaseFilterRequestDTO
   * @see BaseFilterRequestBO
   */
  B toFilter(D filterDTO);

  /**
   * This method is used in conjunction in a PATCH request to refresh any attributes that are present in the already persisted entity, but omitted form the
   * PATCH request.
   *
   * @param source The BaseFilterRequestDTO object that will be mapped from.
   * @param target The BaseFilterRequestBO object that will be mapped to.
   * @see BaseFilterRequestDTO
   * @see BaseFilterRequestBO
   */
  @BeforeMapping
  default void setDirtyFields(RD source, @MappingTarget RB target) {

    iterateFields(target, source, source, "");

  }

  /**
   * This method is used to recursively iterate through all fields in the FilterParameter's class and its superclasses to build the full path of the attributes
   * contained within it.
   *
   * @param target The BaseFilterRequestBO object that will be mapped to.
   * @param source The BaseFilterRequestDTO object that will be mapped from.
   * @param object The object that will be iterated through.
   * @param path   The path that will be used to construct the full path.
   * @see BaseFilterRequestDTO
   * @see BaseFilterRequestBO
   */
  // TODO: Rename method
  private void iterateFields(RB target, RD source, Object object, String path) {
    try {
      // Iterate through all fields in the object's class
      for (Field field : object.getClass()
                               .getDeclaredFields()) {
        // Make the field accessible (in case it's private)
        field.setAccessible(true);

        // Get the value of the field
        Object value = field.get(object);

        // Check if the value is a FilterParameterDTO
        if (value != null && FilterParameterDTO.class.isAssignableFrom(field.getType())) {
          // Construct the full path by concatenating the previous path with the current field name

          String fullPath = path + "." + field.getName();

          // Do something with the FilterParameterDTO
          FilterParameterDTO filterParameterDTO = (FilterParameterDTO) value;
          filterParameterDTO.setAlias(fullPath);
          filterParameterDTO.setPath(fullPath);
          filterParameterDTO.setClazz(Object.class);
          toParameter(filterParameterDTO);

        } else if (value != null) {
          // Construct the new path by concatenating the previous path with the current field name
          String newPath = path.isEmpty() ? field.getName() : path + "." + field.getName();

          // Recursively iterate through the nested object with the new path
          iterateFields(target, source, value, newPath);
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}
