package ca.billybolton.common.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base data-transfer-object, (DTO).
 *
 * @param <K> The type of the id (primary key) field
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BaseDTO", description = "The base class for all DTOs that have a String id.")
public abstract class BaseDTO<K> implements IBaseDTO<K> {

    @Schema(name = "id",
            description =
                    "The id of the entity. The 'ExplPrfx_' is the prefix of the domain object that this DTO " +
                            "represents. "
                            + "For example, the 'ExplPrfx_' for a CandidateDTO is 'CAND'.",
            example = "ExplPrfx_123")
    protected K id;

}
