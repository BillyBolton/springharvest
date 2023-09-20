package dev.springharvest.search.model.queries.parameters.filters;

import dev.springharvest.search.mappers.transformers.IBaseTupleTransformer;
import java.util.Set;

/**
 * This interface is used to contract methods used in filter parameter objects.
 *
 * @author Billy Bolton
 * @see FilterParameterDTO
 * @see FilterParameterBO
 * @since 1.0
 */
public interface IFilterParameter {

  /**
   * This method is used to get the class type of the values within the filter parameter.
   *
   * @return The class of the values within the filter parameter.
   * @see #setValues(Set)
   * @see #getValues()
   */
  Class<?> getClazz();

  /**
   * This method is used to set the class type of the values within the filter parameter.
   *
   * @return The class of the values within the filter parameter.
   * @see #setValues(Set)
   * @see #getValues()
   */
  void setClazz(Class<?> clazz);

  /**
   * This method is used to get the alias of the filter parameter. This alias field is used to identify the TupleElement that is returned from a TupleQuery.
   *
   * @return The alias of the filter parameter.
   * @see IBaseTupleTransformer
   * @see jakarta.persistence.Tuple
   * @see jakarta.persistence.TupleElement
   * @see jakarta.persistence.Tuple#get(String)
   */
  String getAlias();

  /**
   * This method is used to set the alias of the filter parameter. This alias field is used to identify the TupleElement that is returned from a TupleQuery.
   *
   * @see jakarta.persistence.Tuple
   * @see jakarta.persistence.TupleElement
   * @see jakarta.persistence.Tuple#get(String)
   */
  void setAlias(String alias);

  /**
   * This method is used to get the path of the filter parameter.
   *
   * @return The path of the filter parameter.
   */
  CriteriaOperator getOperator();

  /**
   * This method is used to set the criteria operator of the filter parameter.
   *
   * @param operator The criteria operator of the filter parameter.
   * @see CriteriaOperator
   */
  void setOperator(CriteriaOperator operator);

  /**
   * This method is used to get the values of the filter parameter.
   *
   * @return The Set of values of the filter parameter.
   */
  Set<?> getValues();

  /**
   * This method is used to set the values of the filter parameter.
   *
   * @param values The Set of values of the filter parameter.
   */
  void setValues(Set<?> values);

  /**
   * This method is used to identify whether the values in the filter parameter are empty.
   *
   * @return true if the values are empty, otherwise false.
   */
  boolean isEmpty();

}