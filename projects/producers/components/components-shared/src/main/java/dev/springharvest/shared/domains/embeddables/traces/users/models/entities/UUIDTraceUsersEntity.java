package dev.springharvest.shared.domains.embeddables.traces.users.models.entities;

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
public class UUIDTraceUsersEntity extends TraceUsersEntity<UUID> {

}
