package dev.springharvest.shared.domains.labels.models.entities;

import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class LabelEntity<K extends Serializable> extends BaseEntity<K> {

  @Column(name = "label", nullable = false)
  protected String label;

  @Override
  public boolean isEmpty() {
    return id == null && StringUtils.isBlank(label);
  }

}
