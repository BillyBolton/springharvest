package dev.springharvest.shared.domains.embeddables.traces.trace.models.entities;

import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Embeddable
public class UUIDTraceDataEntity extends TraceDataEntity<UUID> {

}
