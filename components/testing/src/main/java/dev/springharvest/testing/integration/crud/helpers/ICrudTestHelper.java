package dev.springharvest.testing.integration.crud.helpers;

import dev.springharvest.testing.integration.shared.helpers.IBaseTestHelper;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public interface ICrudTestHelper<D extends BaseDTO<K>, E extends BaseEntity<K>, K> extends IBaseTestHelper<D, E, K> {

    ValidatableResponse existsById(K id);

    ValidatableResponse findAll();

    ValidatableResponse findById(K id);

    ValidatableResponse create(D dto);

    ValidatableResponse createAll(List<D> dtos);

    ValidatableResponse update(K id, D dto);

    ValidatableResponse updateAll(List<D> dto);

    ValidatableResponse deleteById(K id);

    ValidatableResponse deleteAllByIds(List<K> ids);

}
