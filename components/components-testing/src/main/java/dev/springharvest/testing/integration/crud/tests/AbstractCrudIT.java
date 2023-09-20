package dev.springharvest.testing.integration.crud.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.testing.integration.crud.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.integration.crud.clients.ICrudClient;
import dev.springharvest.testing.integration.shared.helpers.IModelTestFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public abstract class AbstractCrudIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    implements ICrudIT<D, E, K> {

  protected ICrudClient<D, E, K> client;

  protected IModelTestFactory<D, E, K> modelFactory;


  protected AbstractCrudIT(AbstractCrudClientImpl<D, E, K> client, IModelTestFactory<D, E, K> modelFactory) {
    this.client = client;
    this.modelFactory = modelFactory;
  }

  @Nested
  class ConfigTest {

    @Test
    void contextLoads() {
      assertTrue(true);
    }

  }

  @Nested
  class CrudPaths {

    @Nested
    class PostPaths {

      @Test
      void canPostOne() {

        int expectedResponseCode = 500;
        ValidatableResponse response = client.create(modelFactory.buildInvalidDto());
        response.statusCode(expectedResponseCode);

        expectedResponseCode = 200;
        D toCreate = modelFactory.buildValidDto();
        response = client.create(toCreate);
        D created = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getObject("", modelFactory.getClazz());

        toCreate.setId(created.getId());

        SoftAssertions softly = new SoftAssertions();
        modelFactory.softlyAssert(softly, toCreate, created);
        softly.assertAll();
      }

      /**
       * Tests if a list of entities can be created by the API.
       */
      @Test
      void canPostMany() {

        int expectedResponseCode = 200;

        D toCreate = modelFactory.buildValidDto();
        ValidatableResponse response = client.createAll(List.of(toCreate));
        List<D> allCreated = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getList("", modelFactory.getClazz());

        D lastCreated = allCreated.get(allCreated.size() - 1);
        K id = lastCreated.getId();
        toCreate.setId(id);

        SoftAssertions softly = new SoftAssertions();
        modelFactory.softlyAssert(softly, toCreate, lastCreated);
        softly.assertAll();

      }

    }

    @Nested
    class GetPaths {

      @Test
      void canGetOne() {

        int expectedResponseCode = 200;
        ValidatableResponse response = client.findAll();
        List<D> dtos = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getList("", modelFactory.getClazz());
        assertTrue(dtos.size() > 0);
        D firstDto = dtos.get(0);
        K id = firstDto.getId();

        response = client.findById(id);
        D dto = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getObject("", modelFactory.getClazz());
        assertNotNull(dto);
      }

      @Test
      void canGetMany() {

        int expectedResponseCode = 200;
        ValidatableResponse response = client.findAll();
        List<D> dtos = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getList("", modelFactory.getClazz());
        assertTrue(dtos.size() > 0);
      }

    }

    @Nested
    class PatchPaths {

      @Test
      void canUpdateOne() {
        int expectedResponseCode = 200;

        ValidatableResponse response = client.findAll();
        List<D> dtos = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getList("", modelFactory.getClazz());
        assertTrue(dtos.size() > 0);
        D firstDto = dtos.get(dtos.size() - 1);
        K id = firstDto.getId();

        D toUpdate = modelFactory.buildValidUpdatedDto(id);
        response = client.update(id, toUpdate);
        D updated = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getObject("", modelFactory.getClazz());

        SoftAssertions softly = new SoftAssertions();
        modelFactory.softlyAssert(softly, toUpdate, updated);
        softly.assertAll();

      }

      @Test
      void canUpdateMany() {
        int expectedResponseCode = 200;

        ValidatableResponse response = client.findAll();
        List<D> dtos = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getList("", modelFactory.getClazz());
        assertTrue(dtos.size() > 0);
        D firstDto = dtos.get(0);
        K id = firstDto.getId();

        List<D> toUpdate = List.of(modelFactory.buildValidUpdatedDto(firstDto));
        response = client.updateAll(toUpdate);
        List<D> updated = response.statusCode(expectedResponseCode)
            .extract()
            .body()
            .jsonPath()
            .getList("", modelFactory.getClazz());

        SoftAssertions softly = new SoftAssertions();
        modelFactory.softlyAssert(softly, toUpdate, updated);
        softly.assertAll();
      }

    }

    @Nested
    class DeletePaths {

      @Test
      void canDeleteOneAndCanExistsById() {

        D toCreate = modelFactory.buildValidDto();
        ValidatableResponse response = client.create(toCreate);
        D created =
            response.statusCode(200).extract().body().jsonPath().getObject("", modelFactory.getClazz());

        client.deleteById(created.getId()).statusCode(204);
      }

      @Test
      void canDeleteAllByIds() {

        D toCreate = modelFactory.buildValidDto();
        ValidatableResponse response = client.create(toCreate);
        D created =
            response.statusCode(200).extract().body().jsonPath().getObject("", modelFactory.getClazz());

        client.deleteAllByIds(List.of(created.getId())).statusCode(204);
      }

    }

  }

}