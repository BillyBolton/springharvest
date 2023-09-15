package dev.springharvest.testing.integration.crud;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;

import java.io.Serializable;

public interface ICrudIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable> {

}
