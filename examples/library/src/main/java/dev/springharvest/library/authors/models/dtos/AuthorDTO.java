package dev.springharvest.library.authors.models.dtos;

import dev.springhavest.common.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "AuthorDTO", description = "A book's author.")
public class AuthorDTO extends BaseDTO<Long> {

    @Schema(name = "name", description = "The name of the author.", example = "Dr. Seuss")
    protected String name;

    @Schema(name = "id", description = "The id of the author.", example = "1")
    public Long getId() {
        return id;
    }

}
