package dev.springharvest.testing.integration.shared.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import org.assertj.core.api.SoftAssertions;

public abstract class AbstractModelTestFactory<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    implements IModelTestFactory<D, E, K> {

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

  public void softlyAssert(SoftAssertions softly, D actual, D expected) {

    assertNotNull(actual);
    assertNotNull(expected);

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


}
