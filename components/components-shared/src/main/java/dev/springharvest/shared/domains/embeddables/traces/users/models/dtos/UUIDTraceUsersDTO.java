package dev.springharvest.shared.domains.embeddables.traces.users.models.dtos;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UUIDTraceUsersDTO extends AbstractTraceUsersDTO<UUID> {

}
