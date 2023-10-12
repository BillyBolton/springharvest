package dev.springharvest.testing.domains.integration.crud.tests;

import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;

public interface ICrudIT<D extends BaseDTO<K>, K extends Serializable> {

  void softlyAssert(SoftAssertions softly, D actual, D expected);

  void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected);

}
