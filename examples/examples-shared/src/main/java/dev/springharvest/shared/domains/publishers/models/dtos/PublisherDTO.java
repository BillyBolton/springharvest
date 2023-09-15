package dev.springharvest.shared.domains.publishers.models.dtos;

import dev.springhavest.common.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "PublisherDTO", description = "A book's publisher.")
public class PublisherDTO extends BaseDTO<UUID> {

    @Schema(name = "name", description = "The name of the publisher.", example = "Random House")
    protected String name;

    @Schema(name = "id", description = "The id of the publisher.", example = "00000000-0000-0000-0000-000000000001")
    public UUID getId() {
        return id;
    }

}
