package dev.springharvest.testing.integration.shared.factories;

import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;

public abstract class AbstractModelFactory<D extends BaseDTO<K>, K extends Serializable>
    implements IPKModelFactory<D, K> {

}
