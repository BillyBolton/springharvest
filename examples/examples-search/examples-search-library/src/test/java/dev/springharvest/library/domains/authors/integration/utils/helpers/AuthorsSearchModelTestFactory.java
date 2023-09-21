package dev.springharvest.library.domains.authors.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestFactoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorsSearchModelTestFactory
    extends AbstractSearchTestFactoryImpl<AuthorDTO, AuthorEntity, UUID, AuthorFilterRequestDTO> {

  @Override
  public String getIdPath() {
    return AuthorEntityMetadata.Paths.AUTHOR_ID;
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
  public List<SelectionDTO> buildValidSelections(boolean selectAll) {

    if (selectAll) {
      return List.of();
    }
    List<SelectionDTO> selections = new ArrayList<>();
    selections.addAll(List.of(SelectionDTO.builder().alias(AuthorEntityMetadata.Paths.AUTHOR_ID).build(),
                              SelectionDTO.builder().alias(AuthorEntityMetadata.Paths.AUTHOR_NAME).build()));

    return selections;
  }


}
