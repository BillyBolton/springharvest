package dev.springharvest.testing.domains.integration.shared.domains.base.factories;

import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import java.io.Serializable;

public abstract class AbstractModelFactory<D extends BaseDTO<K>, K extends Serializable>
    implements IPKModelFactory<D, K> {

}
