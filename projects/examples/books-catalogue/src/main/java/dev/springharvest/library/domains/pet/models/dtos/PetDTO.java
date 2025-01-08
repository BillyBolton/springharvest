package dev.springharvest.library.domains.pet.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos.AbstractTraceableDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "PetDTO", description = "A Author's pet.")
public class PetDTO extends AbstractTraceableDTO<UUID> {

    @Schema(name = "name", description = "The pet's name", example = "Milo")
    private String name;

    @Override
    @Schema(name = "id", description = "The pet's id.", example = "00000000-0000-0000-0000-000000000001")
    public UUID getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(name);
    }
}
