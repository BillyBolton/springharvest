package dev.springharvest.library.domains.authors.integration.utils.factories;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.testing.domains.integration.search.factories.AbstractSearchModelFactoryImpl;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorSearchModelFactory
    extends AbstractSearchModelFactoryImpl<AuthorDTO, AuthorEntity, AuthorFilterRequestDTO> {

  @Autowired
  public AuthorSearchModelFactory(EntityMetadata<AuthorEntity> entityMetadata) {
    super(entityMetadata);
  }

  @Override
  public AuthorFilterRequestDTO buildValidFilters() {
    return AuthorFilterRequestDTO.builder()
        .author(AuthorFilterDTO.builder()
                    .id(FilterParameterDTO.builder()
                            .values(Set.of(UUID.fromString(
                                "00000000-0000-0000-0000-000000000001")))
                            .operator(CriteriaOperator.EQUALS)
                            .build())
                    .build())
        .build();
  }

  @Override
  public AuthorFilterRequestDTO buildValidFilters(CriteriaOperator operator, List<AuthorDTO> models) {
    Set<UUID> ids = new LinkedHashSet<>();
    Set<String> names = new LinkedHashSet<>();
    models.forEach(model -> {
      ids.add(model.getId());
      names.add(model.getName());
    });
    return AuthorFilterRequestDTO.builder()
        .author(AuthorFilterDTO.builder()
                    .id(FilterParameterDTO.builder()
                            .values(ids)
                            .operator(operator)
                            .build())
                    .name(FilterParameterDTO.builder()
                              .values(names)
                              .operator(operator)
                              .build())
                    .build())
        .build();
  }

}
