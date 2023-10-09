package dev.springharvest.testing.integration.shared.factories;

import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;

public interface IPKModelFactory<D extends BaseDTO<K>, K extends Serializable> {

  K getRandomId();

  default D buildValidUpdatedDto(K id) {
    D dto = buildValidDto();
    dto.setId(id);
    return dto;
  }

  D buildValidDto();

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

}
