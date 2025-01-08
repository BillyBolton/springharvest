package dev.springharvest.library.domains.pet.models.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PetFilterDTO extends BaseFilterDTO {
    @Schema(name = "id", description = "The id of the Pet.")
    private FilterParameterDTO id;

    @Schema(name = "name", description = "The name of the pet.")
    private FilterParameterDTO name;
}
