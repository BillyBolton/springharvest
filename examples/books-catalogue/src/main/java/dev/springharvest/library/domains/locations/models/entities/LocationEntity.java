package dev.springharvest.library.domains.locations.models.entities;

import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "ca_cities")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class LocationEntity extends BaseEntity<Long> {

  @Column(name = "point", columnDefinition = "GEOGRAPHY(POINT)", nullable = false)
  public Point point;
  @Column(name = "name", nullable = false, length = 46)
  private String name;
  @Column(name = "county", length = 36)
  private String county;
  @Column(name = "province", nullable = false, length = 25)
  private String province;
  @Column(name = "province_code", nullable = false, length = 2)
  private String provinceCode;
  @Column(name = "postal_code_area", nullable = false, length = 3)
  private String postalCodeArea;
  @Column(name = "type", nullable = false, length = 19)
  private String type;
  @Column(name = "map_reference", length = 6)
  private String mapReference;
  @Column(name = "latitude", nullable = false)
  private Double latitude;
  @Column(name = "longitude", nullable = false)
  private Double longitude;
  @Column(name = "census_division", nullable = false, length = 36)
  private String censusDivision;
  @Column(name = "area_code", nullable = false, length = 3)
  private String areaCode;
  @Column(name = "time_zone", nullable = false, length = 21)
  private String timeZone;
}