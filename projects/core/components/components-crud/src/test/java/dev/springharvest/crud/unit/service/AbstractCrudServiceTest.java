package dev.springharvest.crud.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class AbstractCrudServiceTest {

  @Mock
  private ICrudRepository<BaseEntity<Long>, Long> crudRepository;
  private BaseEntity<Long> baseEntity;

  private AbstractCrudService<BaseEntity<Long>, Long> crudService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    crudService = new AbstractCrudService<BaseEntity<Long>, Long>(crudRepository) {
    };
    baseEntity = new BaseEntity<Long>() {
    };
  }

  @Test
  void countReturnsCorrectNumberWhenEntitiesExist() {
    when(crudRepository.count()).thenReturn(5L);
    long count = crudService.count();
    assertEquals(5L, count);
  }

  @Test
  void existsByIdReturnsTrueWhenEntityExists() {
    when(crudRepository.existsById(any())).thenReturn(true);
    boolean exists = crudService.existsById(1L);
    assertTrue(exists);
  }

  @Test
  void existsByIdReturnsFalseWhenEntityDoesNotExist() {
    when(crudRepository.existsById(any())).thenReturn(false);
    boolean exists = crudService.existsById(1L);
    assertFalse(exists);
  }

  @Test
  void findByIdReturnsEntityWhenEntityExists() {
    when(crudRepository.findById(any())).thenReturn(Optional.of(baseEntity));
    Optional<BaseEntity<Long>> foundEntity = crudService.findById(1L);
    assertTrue(foundEntity.isPresent());
    assertEquals(baseEntity, foundEntity.get());
  }

  @Test
  void findByIdReturnsEmptyWhenEntityDoesNotExist() {
    when(crudRepository.findById(any())).thenReturn(Optional.empty());
    Optional<BaseEntity<Long>> foundEntity = crudService.findById(1L);
    assertFalse(foundEntity.isPresent());
  }

  @Test
  void createReturnsCreatedEntity() {
    when(crudRepository.save(any())).thenReturn(baseEntity);
    BaseEntity<Long> createdEntity = crudService.create(baseEntity);
    assertEquals(baseEntity, createdEntity);
  }

  @Test
  void updateReturnsUpdatedEntityWhenEntityExists() {
    baseEntity.setId(1L);
    when(crudRepository.existsById(any())).thenReturn(true);
    when(crudRepository.save(any())).thenReturn(baseEntity);
    BaseEntity<Long> updatedEntity = crudService.update(baseEntity);
    assertEquals(baseEntity, updatedEntity);
  }

  @Test
  void updateCreatesNewEntityWhenEntityDoesNotExist() {
    when(crudRepository.existsById(any())).thenReturn(false);
    when(crudRepository.save(any())).thenReturn(baseEntity);
    BaseEntity<Long> updatedEntity = crudService.update(baseEntity);
    assertEquals(baseEntity, updatedEntity);
  }


  @Test
  void findByExampleReturnsEntityWhenEntityExists() {
    when(crudRepository.findOne(any())).thenReturn(Optional.of(baseEntity));
    Optional<BaseEntity<Long>> foundEntity = crudService.findByExample(baseEntity);
    assertTrue(foundEntity.isPresent());
    assertEquals(baseEntity, foundEntity.get());
  }

  @Test
  void findByExampleReturnsEmptyWhenEntityDoesNotExist() {
    when(crudRepository.findOne(any())).thenReturn(Optional.empty());
    Optional<BaseEntity<Long>> foundEntity = crudService.findByExample(baseEntity);
    assertFalse(foundEntity.isPresent());
  }

  @Test
  void findAllByExampleReturnsEntitiesWhenEntitiesExist() {
    List<BaseEntity<Long>> entities = List.of(baseEntity);
    when(crudRepository.findAll(any(Example.class))).thenReturn(entities);
    Iterable<BaseEntity<Long>> foundEntities = crudService.findAllByExample(baseEntity);
    assertEquals(entities, foundEntities);
  }

  @Test
  void findAllByIdsReturnsEntitiesWhenEntitiesExist() {
    List<BaseEntity<Long>> entities = List.of(baseEntity);
    when(crudRepository.findAllById(any())).thenReturn(entities);
    List<BaseEntity<Long>> foundEntities = crudService.findAllByIds(Set.of(1L));
    assertEquals(entities, foundEntities);
  }

  @Test
  @Disabled("TODO: Fix")
  void findAllReturnsEntitiesWhenEntitiesExist() {
    Page<BaseEntity<Long>> pageEntity = mock(Page.class);
    when(pageEntity.hasContent()).thenReturn(true);
    when(crudRepository.findAll(any(Example.class))).thenReturn(pageEntity);
    Page<BaseEntity<Long>> foundEntities = crudService.findAll(PageRequest.of(0, Integer.MAX_VALUE));
    assertEquals(pageEntity, foundEntities);
  }

  @Test
  void createReturnsCreatedEntityWhenEntityDoesNotExist() {
    when(crudRepository.save(any())).thenReturn(baseEntity);
    BaseEntity<Long> createdEntity = crudService.create(baseEntity);
    assertEquals(baseEntity, createdEntity);
  }

  @Test
  void createReturnsCreatedEntitiesWhenEntitiesDoNotExist() {
    List<BaseEntity<Long>> entities = List.of(baseEntity);
    when(crudRepository.saveAll(any())).thenReturn(entities);
    List<BaseEntity<Long>> createdEntities = crudService.create(entities);
    assertEquals(entities, createdEntities);
  }

  @Test
  void updateReturnsUpdatedEntitiesWhenEntitiesExist() {
    List<BaseEntity<Long>> entities = List.of(baseEntity);
    when(crudRepository.save(any())).thenReturn(baseEntity);
    List<BaseEntity<Long>> updatedEntities = crudService.update(entities);
    assertEquals(entities, updatedEntities);
  }

  @Test
  void deleteByIdDoesNotThrowWhenEntityExists() {
    doNothing().when(crudRepository).deleteById(any());
    assertDoesNotThrow(() -> crudService.deleteById(1L));
  }

  @Test
  void deleteByIdsDoesNotThrowWhenEntitiesExist() {
    doNothing().when(crudRepository).deleteAllById(any());
    assertDoesNotThrow(() -> crudService.deleteById(List.of(1L)));
  }

}
