package dev.springharvest.testing.integration.utils.helpers;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.Nullable;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

public interface IBaseCrudTestHelper<D extends BaseDTO<K>, E extends BaseEntity<K>, K> {


    Class<D> getClassType();

    String getIdPath();

    K getRandomId();

    D buildValidDto();

    D buildValidUpdatedDto(K id);

    D buildValidUpdatedDto(D dto);

    D buildInvalidDto();

    E buildValidEntity();

    E buildInvalidEntity();


    void softlyAssert(SoftAssertions softly, @Nullable D actual, @Nullable D expected);

    void softlyAssert(SoftAssertions softly, @Nullable List<D> actual, @Nullable List<D> expected);

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
