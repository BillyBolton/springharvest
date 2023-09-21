package dev.springharvest.testing.integration.shared.helpers;

import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;

public interface IModelTestFactory<D extends BaseDTO<K>, K extends Serializable> {

  K getRandomId();

  D buildValidDto();

  D buildValidUpdatedDto(K id);

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

}
