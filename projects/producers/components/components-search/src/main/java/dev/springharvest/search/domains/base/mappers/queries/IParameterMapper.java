package dev.springharvest.search.domains.base.mappers.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterBO;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionBO;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
import java.util.List;
import java.util.Set;
import javax.naming.OperationNotSupportedException;
import org.mapstruct.Mapping;

/**
 * This interface contracts the methods that are used to map the filter parameter objects.
 *
 * @see FilterParameterDTO
 * @see FilterParameterBO
 * @see SelectionDTO
 * @see SelectionBO
 */
public interface IParameterMapper {

  /**
   * This method is used to map a FilterParameterDTO object to a FilterParameterBO.
   *
   * @param filterParameterDTO The FilterParameterDTO object that will be mapped from.
   * @return The FilterParameterBO object that will be mapped to.
   * @see FilterParameterDTO
   * @see FilterParameterBO
   */
  @Mapping(target = "clazz", expression = "java(getClazz(filterParameterDTO.getPath()))", dependsOn = "path")
  @Mapping(target = "isJoined", expression = "java(getIsJoined(filterParameterDTO.getPath()))")
  FilterParameterBO toParameter(FilterParameterDTO filterParameterDTO);

  /**
   * This method is used to map a SelectionDTO object to a SelectionBO.
   *
   * @param selection The SelectionDTO object that will be mapped from.
   * @return The SelectionBO object that will be mapped to.
   * @see SelectionDTO
   * @see SelectionBO
   */
  @Mapping(target = "alias", source = "alias")
  @Mapping(target = "path", source = "alias")
  @Mapping(target = "clazz", expression = "java(getClazz(selection.getAlias()))", dependsOn = "path")
  @Mapping(target = "isJoined", expression = "java(getIsJoined(selection.getAlias()))")
  SelectionBO toSelection(SelectionDTO selection);

  /**
   * This method is used to map a List of SelectionDTO objects to a List of SelectionBO.
   *
   * @param selection The List of SelectionDTO objects that will be mapped from.
   * @return The List of SelectionBO objects that will be mapped to.
   * @see SelectionDTO
   * @see SelectionBO
   */
  List<SelectionBO> toSelection(List<SelectionDTO> selection);

  /**
   * This method identifies the class type based on the path that is passed. This method should be implemented in the concrete Search mapper.
   *
   * @param path The path that will be used to identify the class type.
   * @return The class type that is identified.
   * @throws OperationNotSupportedException This exception is thrown when the method is not implemented in the concrete Search mapper.
   */
  default Class<?> getClazz(String path) {
    try {
      throw new OperationNotSupportedException("Method should be implemented in the concrete Search mapper.");
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * This method identifies if the path is joined or not.
   *
   * @param path The path that will be used to identify if it is joined or not.
   * @return True if the path is joined, false otherwise.
   */
  default boolean getIsJoined(String path) {
    Set<String> roots = getRoots();
    String prefix = path.split("\\.")[0];
    return !roots.contains(prefix);
  }

  /**
   * This method returns the roots of the paths.
   *
   * @return The roots of the paths.
   */
  default Set<String> getRoots() {
    return Set.of();
  }


}
