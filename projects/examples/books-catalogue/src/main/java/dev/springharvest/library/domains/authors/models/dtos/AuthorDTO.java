package dev.springharvest.library.domains.authors.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.springharvest.library.domains.pet.models.dtos.PetDTO;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos.AbstractTraceableDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "AuthorDTO", description = "A book's author.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO extends AbstractTraceableDTO<UUID> {

  @Schema(name = "name", description = "The name of the author.", example = "Dr. Seuss")
  protected String name;

  @Override
  @Schema(name = "id", description = "The id of the author.", example = "00000000-0000-0000-0000-000000000001")
  public UUID getId() {
    return id;
  }

  private PetDTO pet;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return StringUtils.isBlank(name) && ObjectUtils.isEmpty(id);
  }

}
