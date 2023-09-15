package dev.springharvest.shared.domains.books.models.entities;


import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import dev.springhavest.common.models.entities.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

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

    public boolean isEmpty() {
        return super.isEmpty() && StringUtils.isBlank(title) && (author == null || author.isEmpty()) &&
               (publisher == null || publisher.isEmpty());
    }

}
