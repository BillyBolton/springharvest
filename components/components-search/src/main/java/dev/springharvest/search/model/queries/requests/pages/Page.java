package dev.springharvest.search.model.queries.requests.pages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the page.
 *
 * @author Billy Bolton
 * @since 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Page implements IPageable {

  @Schema(name = "pageNumber", description = "The page number to return. The first page is 1.", defaultValue = "1",
          example = "1")
  @Builder.Default
  private int pageNumber = 1;

  @Schema(name = "pageSize", description = "The number of items to return per page.", defaultValue = "25",
          example = "25")
  @Builder.Default
  private int pageSize = 25;


  @JsonIgnore
  public int getFirstResult() {
    return (pageNumber - 1) * pageSize;
  }

  @JsonIgnore
  public int getMaxResults() {
    return pageSize;
  }

}
