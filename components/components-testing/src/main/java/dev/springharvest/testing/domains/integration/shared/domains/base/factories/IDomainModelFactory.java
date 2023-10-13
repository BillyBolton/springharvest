package dev.springharvest.testing.domains.integration.shared.domains.base.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.TraceDatesDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos.ITraceableDTO;
import dev.springharvest.shared.domains.embeddables.traces.users.models.dtos.AbstractTraceUsersDTO;
import dev.springharvest.shared.utils.StringUtils;
import jakarta.annotation.Nullable;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;

public interface IDomainModelFactory<D extends DomainModel> {

  D buildValidDto();

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

  default void softlyAssert(SoftAssertions softly, List<D> actual, List<D> expected) {
    assertNotNull(actual);
    assertNotNull(expected);
    Assertions.assertEquals(actual.size(), expected.size());

    int count = actual.size() - 1;
    while (count >= 0) {
      softlyAssert(softly, actual.get(count), expected.get(count));
      count--;
    }
  }

  default void softlyAssert(SoftAssertions softly, D actual, D expected) {
    softly.assertThat(actual).isNotNull();
    softly.assertThat(expected).isNotNull();
    if (expected instanceof ITraceableDTO) {
      softly.assertThat(actual instanceof ITraceableDTO).isEqualTo(true);
      // Actual
      TraceDataDTO<?> actualTraceData = ((ITraceableDTO<?>) actual).getTraceData();
      TraceDatesDTO actualTraceDates = actualTraceData.getTraceDates();
      AbstractTraceUsersDTO<?> actualTraceUsers = actualTraceData.getTraceUsers();

      // Expected
      TraceDataDTO<?> expectedTraceData = ((ITraceableDTO<?>) expected).getTraceData();
      TraceDatesDTO expectedTraceDates = expectedTraceData.getTraceDates();
      AbstractTraceUsersDTO<?> expectedTraceUsers = expectedTraceData.getTraceUsers();

      softly.assertThat(actualTraceDates).isNotNull();
      softly.assertThat(expectedTraceDates).isNotNull();
      long createdTimeDifferenceInMilliSeconds = actualTraceDates.getDateCreated().getTime() - expectedTraceDates.getDateUpdated().getTime();
      long createdTimeDifferenceInSeconds = createdTimeDifferenceInMilliSeconds / 1000;
      long createdTimeDifferenceInMinutes = createdTimeDifferenceInSeconds / 60;
      // If the following two assertions fail, then there is a performance issue.
      softly.assertThat(createdTimeDifferenceInSeconds).isLessThan(60);
      softly.assertThat(createdTimeDifferenceInMinutes).isLessThan(1);

      if (ObjectUtils.isNotEmpty(expectedTraceUsers)) {
        // TODO:
//        softly.assertThat(actualTraceUsers.getCreatedBy()).isEqualTo(expectedTraceUsers.getCreatedBy());
//        softly.assertThat(actualTraceUsers.getUpdatedBy()).isEqualTo(expectedTraceUsers.getUpdatedBy());
      }

    }
  }

  default void softlyAssert(SoftAssertions softly, @Nullable Object actual, @Nullable Object expected) {
    if (actual != null && expected != null) {
      if (actual instanceof String actualString && expected instanceof String expectedString) {
        softly.assertThat(StringUtils.capitalizeFirstLetters(actualString)).isEqualTo(StringUtils.capitalizeFirstLetters(expectedString));
      } else {
        softly.assertThat(actual).isEqualTo(expected);
      }
    }
  }
}
