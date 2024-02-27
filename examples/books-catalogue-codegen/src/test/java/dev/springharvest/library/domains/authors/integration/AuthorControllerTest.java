package dev.springharvest.library.domains.authors.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.springharvest.library.domains.authors.author.mappers.IAuthorMapper_;
import dev.springharvest.library.domains.authors.author.rest.AuthorCrudControllerImpl_;
import dev.springharvest.library.domains.authors.author.services.AuthorCrudService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthorCrudControllerImpl_.class)
@DisabledInAotMode
class AuthorControllerTest {

  @MockBean
  IAuthorMapper_ baseModelMapper;

  @MockBean
  AuthorCrudService baseService;

  @Autowired
  MockMvc api;

  @Nested
  class HappyPathTests {

    @Test
    void givenRequestIsAnonymous_whenPostAdmin_thenNotFound() throws Exception {
      api.perform(get("/library/authors/find/all"))
          .andExpect(status().isOk());
    }
  }
}