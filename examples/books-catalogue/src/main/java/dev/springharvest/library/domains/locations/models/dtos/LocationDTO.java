package dev.springharvest.library.domains.locations.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "LocationDTO", description = "An address location.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDTO extends BaseDTO<Long> {

  private String name;
  private String county;
  private String province;
  private String provinceCode;
  private String postalCodeArea;
  private String type;
  private String mapReference;
  private Double latitude;
  private Double longitude;
  private String censusDivision;
  private String areaCode;
  private String timeZone;
//  private Point point;
}
