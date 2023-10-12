package dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos;

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
public class UUIDTraceableDTO extends AbstractTraceableDTO<UUID> {

}