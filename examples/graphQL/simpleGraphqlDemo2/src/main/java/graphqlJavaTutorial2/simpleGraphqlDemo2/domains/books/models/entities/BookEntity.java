package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.books.models.entities;

import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities.AuthorEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "publisher_id")
    protected UUID publisher_id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    // Getters et Setters adaptés

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public AuthorEntity getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(AuthorEntity author) {
//        this.author = author;
//    }

    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(title); // Mis à jour pour vérifier si "title" est vide
    }
}


