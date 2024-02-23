package graphqlJavaTutorial2.simpleGraphqlDemo2.domains.authors.models.entities;

import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "authors")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class AuthorEntity extends AbstractTraceableEntity<UUID> {

    @NotBlank
    @Column(name = "name")
    protected String name;

//    @NotBlank
//    @Column(name = "created_by")
//    protected UUID created_by;

//    @NotBlank
//    @Column(name = "updated_by")
//    protected UUID updated_by;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && StringUtils.isBlank(name);
    }

}
