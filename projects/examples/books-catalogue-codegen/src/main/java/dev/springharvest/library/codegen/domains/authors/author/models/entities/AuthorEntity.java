package dev.springharvest.library.codegen.domains.authors.author.models.entities;

import dev.springharvest.codegen.annotations.Harvest;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "authors")
@AttributeOverride(name = "id", column = @Column(name = "id"))
@Harvest(domainContextPath = "/library", domainNameSingular = "Author", domainNamePlural = "Authors", parentDomainName = "Authors")
public class AuthorEntity extends AbstractTraceableEntity<UUID> {

  @NotBlank
  @Column(name = "name")
  protected String name;

  @Override
  public boolean isEmpty() {
    return super.isEmpty() && StringUtils.isBlank(name);
  }

}
