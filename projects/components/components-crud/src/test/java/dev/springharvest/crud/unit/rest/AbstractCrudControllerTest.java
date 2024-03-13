package dev.springharvest.crud.unit.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.springharvest.crud.domains.base.rest.AbstractCrudController;
import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

class AbstractCrudControllerTest {

  @Mock
  private IBaseModelMapper<BaseDTO<Long>, BaseEntity<Long>, Long> modelMapper;

  @Mock
  private AbstractCrudService<BaseEntity<Long>, Long> crudService;

  private AbstractCrudController<BaseDTO<Long>, BaseEntity<Long>, Long> controller;
  private BaseEntity<Long> baseEntity;
  private BaseDTO<Long> baseDTO;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new AbstractCrudController<BaseDTO<Long>, BaseEntity<Long>, Long>(modelMapper, crudService) {
    };
    baseEntity = new BaseEntity<Long>() {
    };
    baseDTO = new BaseDTO<Long>() {
    };
  }

  @Test
  void countReturnsCorrectValue() {
    when(crudService.count()).thenReturn(5L);
    ResponseEntity<Long> response = controller.count();
    assertEquals(200, response.getStatusCode().value());
    assertEquals(5L, response.getBody());
  }

  @Test
  void findByIdReturnsCorrectEntity() {
    when(crudService.findById(1L)).thenReturn(Optional.of(baseEntity));
    when(modelMapper.entityToDto(baseEntity)).thenReturn(baseDTO);
    ResponseEntity<BaseDTO<Long>> response = controller.findById(1L);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(baseDTO, response.getBody());
  }

  @Test
  void findAllReturnsCorrectEntities() {
    Page<BaseEntity<Long>> pageEntity = mock(Page.class);
    when(pageEntity.hasContent()).thenReturn(true);
    when(pageEntity.getContent()).thenReturn(Collections.singletonList(baseEntity));
    when(crudService.findAll(PageRequest.of(0, Integer.MAX_VALUE))).thenReturn(pageEntity);
    when(modelMapper.entityToDto(baseEntity)).thenReturn(baseDTO);
    ResponseEntity<Page<BaseDTO<Long>>> response = controller.findAll(null, null, null);
    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  void createReturnsCorrectEntity() {
    when(modelMapper.dtoToEntity(baseDTO)).thenReturn(baseEntity);
    when(crudService.create(baseEntity)).thenReturn(baseEntity);
    when(modelMapper.entityToDto(baseEntity)).thenReturn(baseDTO);
    ResponseEntity<BaseDTO<Long>> response = controller.create(baseDTO);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(baseDTO, response.getBody());
  }

  @Test
  void updateReturnsCorrectEntity() {
    baseDTO.setId(1L);
    when(crudService.findById(1L)).thenReturn(Optional.of(baseEntity));
    when(modelMapper.dtoToEntity(baseDTO)).thenReturn(baseEntity);
    when(crudService.update(any(BaseEntity.class))).thenReturn(baseEntity);
    when(modelMapper.entityToDto(any(BaseEntity.class))).thenReturn(baseDTO);
    ResponseEntity<BaseDTO<Long>> response = controller.update(1L, baseDTO);
    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  void deleteByIdReturnsCorrectResponse() {
    doNothing().when(crudService).deleteById(1L);
    ResponseEntity<Void> response = controller.deleteById(1L);
    assertEquals(204, response.getStatusCode().value());
  }
}