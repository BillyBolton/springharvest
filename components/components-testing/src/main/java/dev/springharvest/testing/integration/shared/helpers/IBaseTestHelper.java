package dev.springharvest.testing.integration.shared.helpers;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import jakarta.annotation.Nullable;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public interface IBaseTestHelper<D extends BaseDTO<K>, E extends BaseEntity<K>, K> {

    Class<D> getClassType();

    K getRandomId();

    D buildValidDto();

    D buildValidUpdatedDto(K id);

    D buildValidUpdatedDto(D dto);

    D buildInvalidDto();

    E buildValidEntity();

    E buildInvalidEntity();


    default void softlyAssert(SoftAssertions softly, D actual, D expected) {

        assertNotNull(actual);
        assertNotNull(expected);

    }

    default void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected) {
        assertNotNull(actual);
        assertNotNull(expected);
        assertEquals(actual.size(), expected.size());

        int count = actual.size() - 1;
        while (count >= 0) {
            softlyAssert(softly, actual.get(count), expected.get(count));
            count--;
        }
    }

    default void softlyAssert(SoftAssertions softly, @Nullable Object actual, @Nullable Object expected) {
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

    default String capitalizeFirstLetters(String str) {
        String[] words = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

}
