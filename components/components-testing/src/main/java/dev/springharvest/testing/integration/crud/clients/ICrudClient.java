package dev.springharvest.testing.integration.crud.clients;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;

public interface ICrudClient<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable> {

  ValidatableResponse findAll();

  List<D> extractFindAll();

  ValidatableResponse findById(K id);

  D extractFindById(K id);

  ValidatableResponse create(D dto);

  D extractCreate(D dto);

  ValidatableResponse createAll(List<D> dtos);

  List<D> extractCreateAll(List<D> dtos);

  ValidatableResponse update(K id, D dto);

  D extractUpdate(K id, D dto);

  ValidatableResponse updateAll(List<D> dto);

  List<D> extractUpdateAll(List<D> dto);

  ValidatableResponse deleteById(K id);

  void extractDeleteById(K id);

  ValidatableResponse deleteAllByIds(List<K> ids);

  void extractDeleteByIds(List<K> ids);

}
