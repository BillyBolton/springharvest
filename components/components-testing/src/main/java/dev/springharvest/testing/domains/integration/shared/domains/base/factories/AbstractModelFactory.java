package dev.springharvest.testing.domains.integration.shared.domains.base.factories;

import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import java.io.Serializable;
import lombok.Getter;

@Getter
public abstract class AbstractModelFactory<D extends BaseDTO<K>, K extends Serializable>
    implements IPKModelFactory<D, K> {

  public final Class<D> clazz;

  protected AbstractModelFactory(Class<D> clazz) {
    this.clazz = clazz;
  }

}
