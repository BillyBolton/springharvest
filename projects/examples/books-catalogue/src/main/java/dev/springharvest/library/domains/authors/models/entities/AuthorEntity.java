package dev.springharvest.library.domains.authors.models.entities;

import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "authors")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class AuthorEntity extends AbstractTraceableEntity<UUID> {

  @NotBlank
  @Column(name = "name")
  protected String name;

  @JoinColumn(name = "pet_id")
  @OneToOne(fetch = FetchType.LAZY)
  private PetEntity pet;

  @Override
  public boolean isEmpty() {
    return super.isEmpty() && StringUtils.isBlank(name);
  }

}
