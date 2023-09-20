package dev.springharvest.testing.integration.shared.helpers;

import dev.springhavest.common.contracts.IClazzAware;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;

public interface IModelTestFactory<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable> extends IClazzAware<D> {

  K getRandomId();

  D buildValidDto();

  D buildValidUpdatedDto(K id);

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();


  E buildValidEntity();

  E buildInvalidEntity();

  void softlyAssert(SoftAssertions softly, D actual, D expected);

  void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected);

}
