package dev.springharvest.library.domains.pet.models.entities;

import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "pets")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class PetEntity extends AbstractTraceableEntity<UUID> {
    @NotBlank
    @Column(name = "name")
    private String name;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && StringUtils.isBlank(name);
    }


}
