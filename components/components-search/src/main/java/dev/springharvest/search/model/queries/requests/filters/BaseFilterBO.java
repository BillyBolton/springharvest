package dev.springharvest.search.model.queries.requests.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base filter business object.
 *
 * @author Billy Bolton
 * @see IFilterable
 * @since 1.0
 */
@Data
@SuperBuilder
@AllArgsConstructor
public abstract class BaseFilterBO implements IFilterable {
}
