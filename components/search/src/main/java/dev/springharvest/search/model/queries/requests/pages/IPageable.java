package dev.springharvest.search.model.queries.requests.pages;

/**
 * This interface is used to contract methods used in page objects.
 *
 * @author Billy Bolton
 * @since 1.0
 */
public interface IPageable {

    int getPageNumber();

    void setPageNumber(int pageNumber);

    int getPageSize();

    void setPageSize(int pageSize);

    int getFirstResult();

    int getMaxResults();

}

