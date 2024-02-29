package dev.springharvest.library.domains.publishers.models.entities;


import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.AbstractTraceableEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "publishers")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class PublisherEntity extends AbstractTraceableEntity<UUID> {

    @NotBlank
    @Column(name = "name")
    protected String name;

    @NotNull
    @Column(name = "created_by", insertable = false, updatable = false)
    protected UUID created_by;

    @NotNull
    @Column(name = "updated_by", insertable = false, updatable = false)
    protected UUID updated_by;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && StringUtils.isBlank(name);
    }

}
