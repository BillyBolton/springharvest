package dev.springharvest.crud.unit.rest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
import static org.mockito.Mockito.times;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;

import org.springframework.data.domain.PageImpl;


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

  @Test
  void createAllReturnsCorrectEntities() {
    List<BaseEntity<Long>> entityList = Collections.singletonList(baseEntity);
    List<BaseDTO<Long>> dtoList = Collections.singletonList(baseDTO);

    when(modelMapper.dtoToEntity(dtoList)).thenReturn(entityList);
    when(crudService.create(entityList)).thenReturn(entityList);
    when(modelMapper.entityToDto(entityList)).thenReturn(dtoList);

    ResponseEntity<List<BaseDTO<Long>>> response = controller.createAll(dtoList);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(dtoList, response.getBody());
  }

  @Test
  public void deleteAllByIdPerformsCorrectly() {
    List<Long> ids = Collections.singletonList(1L);
    doNothing().when(crudService).deleteById(ids);

    ResponseEntity<Void> response = controller.deleteAllById(ids);

    verify(crudService, times(1)).deleteById(ids);
    assertEquals(204, response.getStatusCodeValue());
  }

  @Test
  public void updateAllReturnsUpdatedEntities() {
    BaseDTO<Long> dto1 = mock(BaseDTO.class);
    BaseDTO<Long> dto2 = mock(BaseDTO.class);

    when(dto1.getId()).thenReturn(1L);
    when(dto2.getId()).thenReturn(2L);

    List<BaseDTO<Long>> dtos = List.of(dto1, dto2);

    BaseEntity<Long> entity1 = mock(BaseEntity.class);
    BaseEntity<Long> entity2 = mock(BaseEntity.class);

    when(entity1.getId()).thenReturn(1L);
    when(entity2.getId()).thenReturn(2L);

    List<BaseEntity<Long>> entities = List.of(entity1, entity2);

    when(crudService.findAllByIds(anySet())).thenReturn(entities);
    when(modelMapper.entityToDto(anyList())).thenReturn(dtos); //To recheck
    when(modelMapper.dtoToEntity(anyList())).thenReturn(entities);
    when(crudService.update(anyList())).thenReturn(entities);

    ResponseEntity<List<BaseDTO<Long>>> response = controller.updateAll(dtos);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(dtos.size(), response.getBody().size());

    assertTrue(response.getBody().stream().allMatch(dto -> dto.getId() != null && (dto.getId().equals(1L) || dto.getId().equals(2L))));

  }

  @Test
  void findAllHandlesPaginationAndSorting() {
    Page<BaseEntity<Long>> pageEntity = new PageImpl<>(Collections.singletonList(baseEntity));
    Page<BaseDTO<Long>> pageDto = new PageImpl<>(Collections.singletonList(baseDTO));

    when(crudService.findAll(any(PageRequest.class))).thenReturn(pageEntity);
    when(modelMapper.pagedEntityToPagedDto(pageEntity)).thenReturn(pageDto);

    ResponseEntity<Page<BaseDTO<Long>>> response = controller.findAll(0, 1, Collections.singletonList("id-asc"));

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(pageDto, response.getBody());
  }

}