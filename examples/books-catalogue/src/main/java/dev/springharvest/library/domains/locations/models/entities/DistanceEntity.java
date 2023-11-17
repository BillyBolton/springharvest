package dev.springharvest.library.domains.locations.models.entities;

import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.locationtech.jts.geom.Point;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ca_cities_distance")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class DistanceEntity extends BaseEntity<UUID> {

  @Column(name = "source_point", columnDefinition = "GEOGRAPHY(POINT)", nullable = false)
  public Point sourcePoint;

  @Column(name = "target_point", columnDefinition = "GEOGRAPHY(POINT)", nullable = false)
  public Point targetPoint;

  @Column(name = "km_distance", columnDefinition = "GEOGRAPHY(POINT)", nullable = false)
  public Double kmDistance;

}
