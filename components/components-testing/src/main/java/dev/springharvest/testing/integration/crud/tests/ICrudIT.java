package dev.springharvest.testing.integration.crud.tests;

import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;

public interface ICrudIT<D extends BaseDTO<K>, K extends Serializable> {

  void softlyAssert(SoftAssertions softly, D actual, D expected);

  void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected);

}
