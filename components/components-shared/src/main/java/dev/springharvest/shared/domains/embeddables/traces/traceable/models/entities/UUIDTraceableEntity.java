package dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities;

import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UUIDTraceableEntity extends AbstractTraceableEntity<UUID> {

}