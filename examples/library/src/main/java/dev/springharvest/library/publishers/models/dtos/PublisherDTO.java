package dev.springharvest.library.publishers.models.dtos;

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
@Schema(name = "PublisherDTO", description = "A book publisher.")
public class PublisherDTO extends BaseDTO<Long> {

    @Schema(name = "name", description = "The name of the publisher.", example = "1")
    protected String name;

    @Schema(name = "id", description = "The id of the publisher.", example = "1")
    public Long getId() {
        return id;
    }
}
