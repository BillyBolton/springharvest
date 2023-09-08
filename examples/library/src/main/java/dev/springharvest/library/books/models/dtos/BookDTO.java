package dev.springharvest.library.books.models.dtos;

import dev.springharvest.library.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.publishers.models.dtos.PublisherDTO;
import dev.springhavest.common.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "BookDTO", description = "A book.")
public class BookDTO extends BaseDTO<UUID> {

    private String title;

    private AuthorDTO author;

    private PublisherDTO publisher;

    @Schema(name = "id", description = "The id of the book.", example = "1")
    public UUID getId() {
        return id;
    }

}
