package dev.springharvest.search.model.queries.requests.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.search.model.queries.parameters.selections.SelectionBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.pages.Page;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class is used to represent the search request.
 *
 * @author Billy Bolton
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchRequest<RB extends BaseFilterRequestBO> implements ISearchRequest<RB> {

    @Builder.Default
    private Page page = Page.builder()
                            .pageNumber(1)
                            .pageSize(25)
                            .build();

    private List<SelectionBO> selections;

    @Setter
    private Set<RB> filters;

    @JsonIgnore
    public boolean isPageable() {
        return page != null && page.getPageSize() != 0;
    }

    @JsonIgnore
    @Transient
    public PriorityQueue<SelectionBO> getSelectionsByPriority(boolean isAscendingOrder) {
        PriorityQueue<SelectionBO> priorityQueue = new PriorityQueue<>(
                isAscendingOrder ? Comparator.comparingInt(SelectionBO::getPriority)
                                 : Comparator.comparingInt(SelectionBO::getPriority)
                                             .reversed());
        if (!CollectionUtils.isEmpty(selections)) {
            selections.forEach(selection -> {
                if (selection.getPriority() != null) {
                    priorityQueue.add(selection);
                }
            });
        }
        return priorityQueue;
    }

}
