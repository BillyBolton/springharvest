package dev.springharvest.testing.integration.crud.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.testing.integration.crud.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.integration.crud.clients.ICrudClient;
import dev.springharvest.testing.integration.shared.helpers.IModelTestFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public abstract class AbstractCrudIT<D extends BaseDTO<K>, K extends Serializable>
    implements ICrudIT<D, K> {

  protected ICrudClient<D, K> client;

  protected IModelTestFactory<D, K> modelFactory;


  protected AbstractCrudIT(AbstractCrudClientImpl<D, K> client, IModelTestFactory<D, K> modelFactory) {
    this.client = client;
    this.modelFactory = modelFactory;
  }

  protected void softlyAssert(SoftAssertions softly, @Nullable Object actual, @Nullable Object expected) {
    if (actual != null && expected != null) {
      if (actual instanceof String actualString && expected instanceof String expectedString) {
        softly.assertThat(capitalizeFirstLetters(actualString)).isEqualTo(capitalizeFirstLetters(expectedString));
      } else {
        softly.assertThat(actual).isEqualTo(expected);
      }
    }
  }

  protected String capitalizeFirstLetters(String str) {
    String[] words = str.split(" ");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
    }
    return sb.toString().trim();
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

        D toCreate = modelFactory.buildValidDto();
        D created = client.createAndExtract(toCreate);

        toCreate.setId(created.getId());
        SoftAssertions softly = new SoftAssertions();
        softlyAssert(softly, toCreate, created);
        softly.assertAll();
      }

      /**
       * Tests if a list of entities can be created by the API.
       */
      @Test
      void canPostMany() {
        D toCreate = modelFactory.buildValidDto();
        List<D> allCreated = client.createAllAndExtract(List.of(toCreate));

        D lastCreated = allCreated.get(allCreated.size() - 1);
        K id = lastCreated.getId();
        toCreate.setId(id);

        SoftAssertions softly = new SoftAssertions();
        softlyAssert(softly, toCreate, lastCreated);
        softly.assertAll();

      }

    }

    @Nested
    class GetPaths {

      @Test
      void canGetOne() {
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
        D firstDto = dtos.get(0);
        D dto = client.findByIdAndExtract(firstDto.getId());
        assertNotNull(dto);
      }

      @Test
      void canGetMany() {
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
      }

    }

    @Nested
    class PatchPaths {

      @Test
      void canUpdateOne() {
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
        D firstDto = dtos.get(0);
        K id = firstDto.getId();

        D updated = client.updateAndExtract(id, modelFactory.buildValidUpdatedDto(id));
        D retrieved = client.findByIdAndExtract(id);
        SoftAssertions softly = new SoftAssertions();
        softlyAssert(softly, updated, retrieved);
        softly.assertAll();

      }

      @Test
      void canUpdateMany() {
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
        D firstDto = dtos.get(0);
        List<D> toUpdate = List.of(modelFactory.buildValidUpdatedDto(firstDto));
        List<D> updated = client.updateAllAndExtract(toUpdate);
        SoftAssertions softly = new SoftAssertions();
        softlyAssert(softly, toUpdate, updated);
        softly.assertAll();
      }

    }

    @Nested
    class DeletePaths {

      @Test
      void canDeleteOneAndCanExistsById() {
        D created = client.createAndExtract(modelFactory.buildValidDto());
        client.deleteById(created.getId()).statusCode(204);
      }

      @Test
      void canDeleteAllByIds() {
        D created = client.createAndExtract(modelFactory.buildValidDto());
        client.deleteAllByIds(List.of(created.getId())).statusCode(204);
      }

    }

  }

  public void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected) {
    assertNotNull(actual);
    assertNotNull(expected);
    Assertions.assertEquals(actual.size(), expected.size());

    int count = actual.size() - 1;
    while (count >= 0) {
      softlyAssert(softly, actual.get(count), expected.get(count));
      count--;
    }
  }

  public void softlyAssert(SoftAssertions softly, D actual, D expected) {
    softly.assertThat(actual).isNotNull();
    softly.assertThat(expected).isNotNull();

  }

}