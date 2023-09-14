package dev.springharvest.testing.integration.utils.helpers;

import dev.springharvest.testing.integration.utils.clients.RestClientImpl;
import dev.springharvest.testing.integration.utils.uri.AbstractBaseUriFactoryImpl;
import dev.springharvest.testing.integration.utils.uri.IBaseUriFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.Nullable;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractBaseCrudTestHelperImpl<D extends BaseDTO<K>, E extends BaseEntity<K>, K> implements IBaseCrudTestHelper<D, E, K> {

    protected RestClientImpl clientHelper;
    protected IBaseUriFactory uriFactory;

    // Do not remove Autowired here
    @Autowired(required = true)
    protected AbstractBaseCrudTestHelperImpl(RestClientImpl clientHelper, AbstractBaseUriFactoryImpl uriFactory) {
        this.clientHelper = clientHelper;
        this.uriFactory = uriFactory;

    }

    @Override
    public void softlyAssert(SoftAssertions softly, D actual, D expected) {

        assertNotNull(actual);
        assertNotNull(expected);

    }

    @Override
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

    @Override
    public ValidatableResponse existsById(K id) {
        return clientHelper.getAndThen(uriFactory.getExistsByIdUri(), id);
    }

    @Override
    public ValidatableResponse findAll() {
        return clientHelper.getAndThen(uriFactory.getFindAllUri());
    }

    @Override
    public ValidatableResponse findById(K id) {
        return clientHelper.getAndThen(uriFactory.getFindByIdUri(), id);
    }

    @Override
    public ValidatableResponse create(D dto) {
        return clientHelper.postAndThen(uriFactory.getPostUri(), dto);
    }

    @Override
    public ValidatableResponse createAll(List<D> dtos) {
        return clientHelper.postAndThen(uriFactory.getPostAllUri(), dtos);
    }

    @Override
    public ValidatableResponse update(K id, D dto) {
        return clientHelper.patchAndThen(uriFactory.getPatchUri(), dto, id);
    }

    @Override
    public ValidatableResponse updateAll(List<D> dtos) {
        return clientHelper.patchAndThen(uriFactory.getPatchAllUri(), dtos);
    }

    @Override
    public ValidatableResponse deleteById(K id) {
        return clientHelper.deleteAndThen(uriFactory.getDeleteByIdUri(), id);
    }

    @Override
    public ValidatableResponse deleteAllByIds(List<K> ids) {
        return clientHelper.deleteAllAndThen(uriFactory.getDeleteAllByIdsUri(), ids);
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

}
