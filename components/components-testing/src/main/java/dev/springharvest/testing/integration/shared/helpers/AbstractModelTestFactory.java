package dev.springharvest.testing.integration.shared.helpers;

import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;

public abstract class AbstractModelTestFactory<D extends BaseDTO<K>, K extends Serializable>
    implements IModelTestFactory<D, K> {


}
