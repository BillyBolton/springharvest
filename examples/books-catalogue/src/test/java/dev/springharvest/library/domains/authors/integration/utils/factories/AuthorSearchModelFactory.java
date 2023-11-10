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
import java.util.LinkedList;
import java.util.List;
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
  public AuthorFilterRequestDTO buildValidUniqueFilters(CriteriaOperator operator, List<AuthorDTO> models) {
    List<UUID> ids = new LinkedList<>();
    List<String> names = new LinkedList<>();
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
