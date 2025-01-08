package dev.springharvest.library.domains.pet.models.queries;


import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PetFilterRequestDTO extends BaseFilterRequestDTO {

    private PetFilterDTO pet;
}
