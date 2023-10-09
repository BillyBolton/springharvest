package dev.springharvest.library.domains.books.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springhavest.common.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "BookDTO", description = "A book.")
public class BookDTO extends BaseDTO<UUID> {

  @Schema(name = "title", description = "The title of the book.", example = "The Cat in the Hat")
  private String title;

  private AuthorDTO author;

  private PublisherDTO publisher;

  @Override
  @Schema(name = "id", description = "The id of the book.", example = "00000000-0000-0000-0000-000000000001")
  public UUID getId() {
    return id;
  }

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return StringUtils.isBlank(title) && (author == null || author.isEmpty()) && (publisher == null || publisher.isEmpty());
  }
}
