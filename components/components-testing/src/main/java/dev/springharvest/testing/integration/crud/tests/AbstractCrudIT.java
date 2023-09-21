package dev.springharvest.testing.integration.crud.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.testing.integration.crud.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.integration.crud.clients.ICrudClient;
import dev.springharvest.testing.integration.shared.helpers.IModelTestFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public abstract class AbstractCrudIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    implements ICrudIT<D, E, K> {

  protected ICrudClient<D, E, K> client;

  protected IModelTestFactory<D, K> modelFactory;


  protected AbstractCrudIT(AbstractCrudClientImpl<D, E, K> client, IModelTestFactory<D, K> modelFactory) {
    this.client = client;
    this.modelFactory = modelFactory;
  }

  protected void softlyAssert(SoftAssertions softly, @Nullable Object actual, @Nullable Object expected) {
    if (actual != null && expected != null) {
      if (actual instanceof String && expected instanceof String) {
        String actualString = capitalizeFirstLetters((String) actual);
        String expectedString = capitalizeFirstLetters((String) expected);
        softly.assertThat(actualString).isEqualTo(expectedString);
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

        expectedResponseCode = 200;
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

        int expectedResponseCode = 200;

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
        assertTrue(dtos.size() > 0);
        D firstDto = dtos.get(0);
        D dto = client.findByIdAndExtract(firstDto.getId());
        assertNotNull(dto);
      }

      @Test
      void canGetMany() {
        List<D> dtos = client.findAllAndExtract();
        assertTrue(dtos.size() > 0);
      }

    }

    @Nested
    class PatchPaths {

      @Test
      void canUpdateOne() {
        List<D> dtos = client.findAllAndExtract();
        assertTrue(dtos.size() > 0);
        D firstDto = dtos.get(dtos.size() - 1);
        K id = firstDto.getId();

        D toUpdate = modelFactory.buildValidUpdatedDto(id);
        D updated = client.updateAndExtract(id, toUpdate);

        SoftAssertions softly = new SoftAssertions();
        updated.getClass();
        softlyAssert(softly, toUpdate, updated);
        softly.assertAll();

      }

      @Test
      void canUpdateMany() {
        List<D> dtos = client.findAllAndExtract();
        assertTrue(dtos.size() > 0);
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
    assertEquals(actual.size(), expected.size());

    int count = actual.size() - 1;
    while (count >= 0) {
      softlyAssert(softly, actual.get(count), expected.get(count));
      count--;
    }
  }

  public void softlyAssert(SoftAssertions softly, D actual, D expected) {

    assertNotNull(actual);
    assertNotNull(expected);

  }


}