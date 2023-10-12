package dev.springharvest.library.domains.books.models.entities;


import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "books")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class BookEntity extends BaseEntity<UUID> {

  @NotBlank
  @Column(name = "title")
  private String title;

  @NotNull
  @JoinColumn(name = "author_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private AuthorEntity author;

  @NotNull
  @JoinColumn(name = "publisher_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private PublisherEntity publisher;

  @Override
  public boolean isEmpty() {
    return super.isEmpty() && StringUtils.isBlank(title) && (author == null || author.isEmpty()) &&
           (publisher == null || publisher.isEmpty());
  }

}
