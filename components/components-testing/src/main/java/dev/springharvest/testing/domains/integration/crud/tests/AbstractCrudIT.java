package dev.springharvest.testing.domains.integration.crud.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.ICrudClient;
import dev.springharvest.testing.domains.integration.shared.domains.base.factories.IPKModelFactory;
import dev.springharvest.testing.domains.integration.shared.tests.AbstractBaseIT;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
public abstract class AbstractCrudIT<D extends BaseDTO<K>, K extends Serializable>
    extends AbstractBaseIT
    implements ICrudIT<D, K> {

  protected ICrudClient<D, K> client;

  protected IPKModelFactory<D, K> modelFactory;


  protected AbstractCrudIT(AbstractCrudClientImpl<D, K> client, IPKModelFactory<D, K> modelFactory) {
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
        log.debug("canPostOne for {}", modelFactory.getClazz().getSimpleName());
        int expectedResponseCode = 500;
        ValidatableResponse response = client.create(modelFactory.buildInvalidDto());
        response.statusCode(expectedResponseCode);

        D toCreate = modelFactory.buildValidDto();
        D created = client.createAndExtract(toCreate);

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
        log.debug("canPostMany for {}", modelFactory.getClazz().getSimpleName());
        D toCreate = modelFactory.buildValidDto();
        List<D> allCreated = client.createAllAndExtract(List.of(toCreate));

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

      private static Stream<Arguments> findAllArgumentsProvider() {
        List<Integer> pageNumberProvider = List.of(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        List<Integer> pageSizeProvider = List.of(0, 1, 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
        List<String> sortsProvider = List.of("id-asc", "id-desc", "id-asc,id-desc", ",", "", RandomStringUtils.randomNumeric(5));

        return pageNumberProvider.stream()
            .flatMap(pageNumber -> pageSizeProvider.stream()
                .flatMap(pageSize -> sortsProvider.stream()
                    .map(sorts -> Arguments.of(pageNumber, pageSize, sorts))));
      }

      @Test
      void canFindById() {
        log.debug("canFindById for {}", modelFactory.getClazz().getSimpleName());
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
        D firstDto = dtos.get(0);
        D dto = client.findByIdAndExtract(firstDto.getId());
        assertNotNull(dto);
      }

      @ParameterizedTest
      @MethodSource("findAllArgumentsProvider")
      void canFindWithArguments(Integer pageNumber, Integer pageSize, String sorts) {
        log.debug("canFindWithArguments for {}", modelFactory.getClazz().getSimpleName());
        int createCount = 5;
        client.deleteAllByIds(client.findAllAndExtract().stream().map(BaseDTO::getId).toList());
        Assertions.assertEquals(0, client.findAllAndExtract().size());
        List<D> createdDtos = client.createAllAndExtract(modelFactory.buildValidDto(createCount));
        List<D> dtos = client.findAllAndExtract(pageNumber, pageSize, sorts);
        Assertions.assertEquals(createCount,
                                createdDtos.size(),
                                "The number of created dtos should be equal to the pageSize. :: " + modelFactory.getClazz().getSimpleName());

        boolean isPageable = pageNumber != null && pageNumber >= 0 && pageSize != null && pageSize >= 0;
        int expectedCount = createCount;
        if (isPageable) {
          int pageCount = createCount < pageSize || pageSize == 0 ? createCount : createCount / pageSize;
          expectedCount = pageCount * (pageNumber + 1) > createCount ? 0 : pageCount;
        }

        Assertions.assertEquals(expectedCount, dtos.size(),
                                "The number of retrieved dtos should be equal to the calculated pageSize. :: " + modelFactory.getClazz().getSimpleName());
      }

      @Test
      void canFindAll() {
        log.debug("canFindAll for {}", modelFactory.getClazz().getSimpleName());
        client.deleteAllByIds(client.findAllAndExtract().stream().map(BaseDTO::getId).toList());
        int createCount = 5;
        List<D> createdDtos = client.createAllAndExtract(modelFactory.buildValidDto(createCount));
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertEquals(createCount,
                                createdDtos.size(),
                                "The number of created dtos should be equal to the number of dtos returned by the findAll method. :: " +
                                modelFactory.getClazz().getSimpleName());
        Assertions.assertEquals(createCount,
                                dtos.size(),
                                "The number of created dtos should be equal to the number of dtos returned by the findAll method. :: " +
                                modelFactory.getClazz().getSimpleName());
      }
    }

    @Nested
    class PatchPaths {

      @Test
      void canUpdateOne() {
        log.debug("canUpdateOne for {}", modelFactory.getClazz().getSimpleName());
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
        D firstDto = dtos.get(0);
        K id = firstDto.getId();
        firstDto = modelFactory.buildValidUpdatedDto(firstDto);
        D updated = client.updateAndExtract(id, firstDto);
        D retrieved = client.findByIdAndExtract(id);
        SoftAssertions softly = new SoftAssertions();
        modelFactory.softlyAssert(softly, updated, retrieved);
        softly.assertAll();

      }

      @Test
      void canUpdateMany() {
        log.debug("canUpdateMany for {}", modelFactory.getClazz().getSimpleName());
        List<D> dtos = client.findAllAndExtract();
        Assertions.assertFalse(dtos.isEmpty());
        D firstDto = dtos.get(0);
        List<D> toUpdate = List.of(modelFactory.buildValidUpdatedDto(firstDto));
        List<D> updated = client.updateAllAndExtract(toUpdate);
        SoftAssertions softly = new SoftAssertions();
        modelFactory.softlyAssert(softly, toUpdate, updated);
        softly.assertAll();
      }

    }

    @Nested
    class DeletePaths {

      @Test
      void canDeleteOneAndCanExistsById() {
        log.debug("canDeleteOneAndCanExistsById for {}", modelFactory.getClazz().getSimpleName());
        D created = client.createAndExtract(modelFactory.buildValidDto());
        client.deleteById(created.getId()).statusCode(204);
      }

      @Test
      void canDeleteAllByIds() {
        log.debug("canDeleteAllByIds for {}", modelFactory.getClazz().getSimpleName());
        D created = client.createAndExtract(modelFactory.buildValidDto());
        client.deleteAllByIds(List.of(created.getId())).statusCode(204);
      }

    }

  }

}