package dev.springharvest.testing.integration.shared.factories;

import dev.springhavest.common.models.domains.DomainModel;

public interface IDomainModelFactory<D extends DomainModel> {
  
  D buildValidDto();

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

}
