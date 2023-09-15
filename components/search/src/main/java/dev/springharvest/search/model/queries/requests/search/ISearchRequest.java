package dev.springharvest.search.model.queries.requests.search;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;

import java.util.Set;

/**
 * This interface is used to contract methods used in search objects.
 *
 * @author Billy Bolton
 * @since 1.0
 */
public interface ISearchRequest<F extends BaseFilterRequestBO> {

    Set<F> getFilters();

    void setFilters(Set<F> filters);

}
