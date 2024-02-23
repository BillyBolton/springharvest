package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.models.entities;

import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.publisher.models.entities.PublisherEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

@Entity
@Table(name = "books")
public class BookEntity extends AbstractTraceableEntity<UUID> {

    @NotBlank
    @Column(name = "title")
    protected String title;

    @NotBlank
    @Column(name = "genre")
    protected String genre;

    // Auhtor Getters Setters
    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @NotNull
    @JoinColumn(name = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PublisherEntity publisher;


    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(title); // Check if "title" is empty
    }

//    @Override
//    public boolean isEmpty() {
//        return super.isEmpty() && StringUtils.isBlank(title) && (author == null || author.isEmpty()) &&
//                (publisher == null || publisher.isEmpty());
//    }
}


