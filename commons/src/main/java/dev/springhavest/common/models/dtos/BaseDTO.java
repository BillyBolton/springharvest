package dev.springhavest.common.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * This class is used to represent the base data-transfer-object, (DTO).
 *
 * @param <K> The type of the id (primary key) field
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "BaseDTO", description = "The base class for all DTOs that have a String id.")
public abstract class BaseDTO<K extends Serializable> implements IBaseDTO<K> {

    @Schema(name = "id",
            description = "The id of the entity. The 'ExplPrfx_' is the prefix of the domain object that this DTO " +
                          "represents. " + "For example, the 'ExplPrfx_' for a CandidateDTO is 'CAND'.",
            example = "ExplPrfx_123")
    protected K id;

}
