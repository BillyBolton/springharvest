package dev.springharvest.testing.domains.integration.shared.factories;

import dev.springharvest.shared.domains.DomainModel;

public interface IDomainModelFactory<D extends DomainModel> {

  D buildValidDto();

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

}
