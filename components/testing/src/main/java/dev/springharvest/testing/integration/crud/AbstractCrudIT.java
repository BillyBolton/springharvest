package dev.springharvest.testing.integration.crud;

import dev.springharvest.testing.integration.crud.helpers.AbstractCrudTestHelperImpl;
import dev.springharvest.testing.integration.crud.helpers.ICrudTestHelper;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractCrudIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K> implements ICrudIT<D, E, K> {

    protected RestClientImpl clientHelper;
    protected ICrudTestHelper<D, E, K> testHelper;

    protected AbstractCrudIT(RestClientImpl clientHelper, AbstractCrudTestHelperImpl<D, E, K> testHelper) {
        this.clientHelper = clientHelper;
        this.testHelper = testHelper;
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
                ValidatableResponse response = testHelper.create(testHelper.buildInvalidDto());
                response.statusCode(expectedResponseCode);

                expectedResponseCode = 200;
                D toCreate = testHelper.buildValidDto();
                response = testHelper.create(toCreate);
                D created = response.statusCode(expectedResponseCode)
                                    .extract()
                                    .body()
                                    .jsonPath()
                                    .getObject("", testHelper.getClassType());

                toCreate.setId(created.getId());

                SoftAssertions softly = new SoftAssertions();
                testHelper.softlyAssert(softly, toCreate, created);
                softly.assertAll();
            }

            /**
             * Tests if a list of entities can be created by the API.
             */
            @Test
            void canPostMany() {

                int expectedResponseCode = 200;

                D toCreate = testHelper.buildValidDto();
                ValidatableResponse response = testHelper.createAll(List.of(toCreate));
                List<D> allCreated = response.statusCode(expectedResponseCode)
                                             .extract()
                                             .body()
                                             .jsonPath()
                                             .getList("", testHelper.getClassType());

                D lastCreated = allCreated.get(allCreated.size() - 1);
                K id = lastCreated.getId();
                toCreate.setId(id);

                SoftAssertions softly = new SoftAssertions();
                testHelper.softlyAssert(softly, toCreate, lastCreated);
                softly.assertAll();

            }

        }

    }

    @Nested
    class GetPaths {

        @Test
        void canGetOne() {

            int expectedResponseCode = 200;
            ValidatableResponse response = testHelper.findAll();
            List<D> dtos = response.statusCode(expectedResponseCode)
                                   .extract()
                                   .body()
                                   .jsonPath()
                                   .getList("", testHelper.getClassType());
            assertTrue(dtos.size() > 0);
            D firstDto = dtos.get(0);
            K id = firstDto.getId();

            response = testHelper.findById(id);
            D dto = response.statusCode(expectedResponseCode)
                            .extract()
                            .body()
                            .jsonPath()
                            .getObject("", testHelper.getClassType());
            assertNotNull(dto);
        }

        @Test
        void canGetMany() {

            int expectedResponseCode = 200;
            ValidatableResponse response = testHelper.findAll();
            List<D> dtos = response.statusCode(expectedResponseCode)
                                   .extract()
                                   .body()
                                   .jsonPath()
                                   .getList("", testHelper.getClassType());
            assertTrue(dtos.size() > 0);
        }

    }

    @Nested
    class PatchPaths {

        @Test
        void canUpdateOne() {
            int expectedResponseCode = 200;

            ValidatableResponse response = testHelper.findAll();
            List<D> dtos = response.statusCode(expectedResponseCode)
                                   .extract()
                                   .body()
                                   .jsonPath()
                                   .getList("", testHelper.getClassType());
            assertTrue(dtos.size() > 0);
            D firstDto = dtos.get(dtos.size() - 1);
            K id = firstDto.getId();

            D toUpdate = testHelper.buildValidUpdatedDto(id);
            response = testHelper.update(id, toUpdate);
            D updated = response.statusCode(expectedResponseCode)
                                .extract()
                                .body()
                                .jsonPath()
                                .getObject("", testHelper.getClassType());


            SoftAssertions softly = new SoftAssertions();
            testHelper.softlyAssert(softly, toUpdate, updated);
            softly.assertAll();

        }

        @Test
        void canUpdateMany() {
            int expectedResponseCode = 200;

            ValidatableResponse response = testHelper.findAll();
            List<D> dtos = response.statusCode(expectedResponseCode)
                                   .extract()
                                   .body()
                                   .jsonPath()
                                   .getList("", testHelper.getClassType());
            assertTrue(dtos.size() > 0);
            D firstDto = dtos.get(0);
            K id = firstDto.getId();

            List<D> toUpdate = List.of(testHelper.buildValidUpdatedDto(firstDto));
            response = testHelper.updateAll(toUpdate);
            List<D> updated = response.statusCode(expectedResponseCode)
                                      .extract()
                                      .body()
                                      .jsonPath()
                                      .getList("", testHelper.getClassType());

            SoftAssertions softly = new SoftAssertions();
            testHelper.softlyAssert(softly, toUpdate, updated);
            softly.assertAll();
        }

    }

    @Nested
    class DeletePaths {

        @Test
        void canDeleteOneAndCanExistsById() {

            D toCreate = testHelper.buildValidDto();
            ValidatableResponse response = testHelper.create(toCreate);
            D created = response.statusCode(200).extract().body().jsonPath().getObject("", testHelper.getClassType());

            testHelper.deleteById(created.getId()).statusCode(204);
        }

        @Test
        void canDeleteAllByIds() {

            D toCreate = testHelper.buildValidDto();
            ValidatableResponse response = testHelper.create(toCreate);
            D created = response.statusCode(200).extract().body().jsonPath().getObject("", testHelper.getClassType());

            testHelper.deleteAllByIds(List.of(created.getId())).statusCode(204);
        }

    }

}