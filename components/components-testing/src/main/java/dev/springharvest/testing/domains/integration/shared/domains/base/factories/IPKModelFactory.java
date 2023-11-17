package dev.springharvest.testing.domains.integration.shared.domains.base.factories;

import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import java.io.Serializable;
import java.util.List;
import java.util.stream.IntStream;

public interface IPKModelFactory<D extends BaseDTO<K>, K extends Serializable> extends IDomainModelFactory<D> {

  Class<D> getClazz();

  K getRandomId();

  default List<D> buildValidDto(int count) {
    final int minCount = 1;
    final int maxCount = 101;
    if (count < minCount || count > maxCount) {
      throw new IllegalArgumentException(String.format("When batch creating entities for testing, the count should be between %s and %s.", minCount, maxCount));
    }
    return IntStream.range(0, count)
        .parallel()
        .mapToObj(i -> buildValidDto())
        .toList();
  }

  D buildValidDto();

  D buildValidUpdatedDto(D dto);

  D buildInvalidDto();

  default D buildValidUpdatedDto(K id) {
    D dto = buildValidDto();
    dto.setId(id);
    return dto;
  }

}
