package dev.springharvest.testing.integration.shared.helpers;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;

public abstract class AbstractModelTestFactory<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    implements IModelTestFactory<D, E, K> {


}
