package dev.springharvest.testing.integration.crud.clients;

import dev.springhavest.common.models.dtos.BaseDTO;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;

public interface ICrudClient<D extends BaseDTO<K>, K extends Serializable> {

  ValidatableResponse findAll();

  List<D> findAllAndExtract();

  ValidatableResponse findById(K id);

  D findByIdAndExtract(K id);

  ValidatableResponse create(D dto);

  D createAndExtract(D dto);

  ValidatableResponse createAll(List<D> dtos);

  List<D> createAllAndExtract(List<D> dtos);

  ValidatableResponse update(K id, D dto);

  D updateAndExtract(K id, D dto);

  ValidatableResponse updateAll(List<D> dto);

  List<D> updateAllAndExtract(List<D> dto);

  ValidatableResponse deleteById(K id);

  void deleteByIdAndExtract(K id);

  ValidatableResponse deleteAllByIds(List<K> ids);

  void deleteByIdsAndExtract(List<K> ids);

}
