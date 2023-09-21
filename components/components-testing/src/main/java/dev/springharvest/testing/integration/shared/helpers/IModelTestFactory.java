package dev.springharvest.testing.integration.shared.helpers;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;

public interface IModelTestFactory<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable> {

  K getRandomId();

  D buildValidDto();

  D buildValidUpdatedDto(K id);

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

//  void softlyAssert(SoftAssertions softly, D actual, D expected);
//
//  void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected);

}
