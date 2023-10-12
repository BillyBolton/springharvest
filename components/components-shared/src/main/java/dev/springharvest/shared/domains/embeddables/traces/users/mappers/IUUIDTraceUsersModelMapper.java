package dev.springharvest.shared.domains.embeddables.traces.users.mappers;

import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface IUUIDTraceUsersModelMapper extends ITraceUsersModelMapper<UUID> {

}
