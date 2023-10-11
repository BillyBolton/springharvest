package dev.springhavest.common.mappers;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;

public interface ITraceableModelMapper<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    extends IBaseModelMapper<D, E, K> {

}
