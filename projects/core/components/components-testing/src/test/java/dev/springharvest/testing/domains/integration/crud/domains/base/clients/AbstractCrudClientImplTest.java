package dev.springharvest.testing.domains.integration.crud.domains.base.clients;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.uri.CrudUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ResponseBodyData;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;



class AbstractCrudClientImplTest {


    @Mock
    private RestClientImpl clientHelper;

    @Mock
    private CrudUriFactory uriFactory;

    @Mock
    private AbstractCrudClientImpl<TestDTO, Long> crudClient;

    @Mock
    private ValidatableResponse mockResponse;

    private TestCrudClient testCrudClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCrudClient = new TestCrudClient(clientHelper, uriFactory);
        crudClient = mock(AbstractCrudClientImpl.class);
    }

    @Test
    void testFindAll() {
        when(uriFactory.getFindAllUri(null, null, null)).thenReturn("findAllUri");
        when(clientHelper.getAndThen("findAllUri")).thenReturn(mockResponse);

        ValidatableResponse response = testCrudClient.findAll();

        verify(clientHelper).getAndThen("findAllUri");
        assertEquals(mockResponse, response, "The response should match the mocked response");
    }

    @Test
    void testFindById() {
        when(uriFactory.getFindByIdUri()).thenReturn("findByIdUri");
        when(clientHelper.getAndThen("findByIdUri", 1L)).thenReturn(mockResponse);

        ValidatableResponse response = testCrudClient.findById(1L);

        verify(clientHelper).getAndThen("findByIdUri", 1L);
        assertEquals(mockResponse, response, "The response should match the mocked response");
    }

    @Test
    void testCreate() {
        TestDTO dto = new TestDTO();
        when(uriFactory.getPostUri()).thenReturn("postUri");
        when(clientHelper.postAndThen("postUri", dto)).thenReturn(mockResponse);

        ValidatableResponse response = testCrudClient.create(dto);

        verify(clientHelper).postAndThen("postUri", dto);
        assertEquals(mockResponse, response, "The response should match the mocked response");
    }

    
    

    @Test
    void testDeleteByIdAndExtract() {
        when(uriFactory.getDeleteByIdUri()).thenReturn("deleteByIdUri");
        when(clientHelper.deleteAndThen("deleteByIdUri", 1L)).thenReturn(mockResponse);

        testCrudClient.deleteByIdAndExtract(1L);

        verify(clientHelper).deleteAndThen("deleteByIdUri", 1L);
        verify(mockResponse).statusCode(204);
    }

    static class TestCrudClient extends AbstractCrudClientImpl<TestDTO, Long> {
        public TestCrudClient(RestClientImpl clientHelper, CrudUriFactory uriFactory) {
            super(clientHelper, uriFactory, TestDTO.class);
        }
    }

    
    static class TestDTO extends BaseDTO<Long> {
        private Long id;

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }
    }
}
