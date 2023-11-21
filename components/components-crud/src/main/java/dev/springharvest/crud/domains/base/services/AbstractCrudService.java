package dev.springharvest.crud.domains.base.services;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.ITraceableEntity;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.TraceUsersEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class AbstractCrudService<E extends BaseEntity<K>, K extends Serializable>
    implements ICrudService<E, K> {

  protected ICrudRepository<E, K> crudRepository;

  protected AbstractCrudService(ICrudRepository<E, K> crudRepository) {
    this.crudRepository = crudRepository;
  }


  protected Class<E> getClazz() {
    ParameterizedType paramType = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<E>) paramType.getActualTypeArguments()[1];
  }

  @Override
  @Transactional(readOnly = true)
  public long count() {
    return crudRepository.count();
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsById(K id) {
    return crudRepository.existsById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<E> findById(K id) {
    return crudRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<E> findByExample(E entity) {
    return crudRepository.findOne(Example.of(entity));
  }

  @Override
  @Transactional(readOnly = true)
  public List<E> findAllByIds(Set<K> ids) {
    return crudRepository.findAllById(ids);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<E> findAll(Pageable pageable) {
    return crudRepository.findAll(pageable);
  }

  @Transactional
  public E create(@Valid E entity) {
    entity = beforeCreation(entity);
    try {
      return crudRepository.save(entity);
    } catch (EntityExistsException e) {
      log.error("Entity already exists", e);
      throw e;
    }
  }

  @Transactional
  public List<E> create(@Valid List<E> entities) {
    return crudRepository.saveAll(beforeCreation(entities));
  }

  @Transactional
  public E update(@Valid E entity) {
    return crudRepository.save(beforeUpdate(entity));
  }

  @Transactional
  public List<E> update(@Valid List<E> entities) {
    return entities.stream().map(this::update).toList();
  }

  @Transactional
  public void deleteById(K id) {
    crudRepository.deleteById(id);
  }

  @Transactional
  public void deleteById(List<K> ids) {
    crudRepository.deleteAllById(ids);
  }

  protected E beforeUpdate(E source) {
    validate(source);
    K id = source.getId();
    if (!existsById(id)) {
      return create(source);
    }
    if (source instanceof ITraceableEntity<?>) {
      TraceDataEntity<K> traceData = ((ITraceableEntity) source).getTraceData();
      if (traceData != null) {
        if (ObjectUtils.isNotEmpty(traceData.getTraceDates())) {
          TraceDatesEntity traceDates = traceData.getTraceDates();
          final Date utcTimeStamp = Date.from(Instant.now());
          traceDates.setDateUpdated(utcTimeStamp);
          traceData.setTraceDates(traceDates);
        }
        if (ObjectUtils.isNotEmpty(traceData.getTraceUsers())) {
          TraceUsersEntity traceUsers = traceData.getTraceUsers();
          // For Now
          log.info("TODO: Place holder for setting created user.");
          traceData.setTraceUsers(traceUsers);
        }
      }
    }
    return source;
  }

  protected void validate(E entity) {
    // TODO: implement update validation
  }

  protected List<E> beforeCreation(List<E> source) {
    source.forEach(this::beforeCreation);
    return source;
  }

  protected E beforeCreation(E entity) {
    entity.setId(null);

    if (entity instanceof ITraceableEntity<?>) {
      final Date utcTimeStamp = Date.from(Instant.now());
      ((ITraceableEntity<Serializable>) entity).setTraceData(TraceDataEntity.builder()
                                                                 .traceDates(TraceDatesEntity.builder()
                                                                                 .dateCreated(utcTimeStamp)
                                                                                 .dateUpdated(utcTimeStamp)
                                                                                 .build())
                                                                 .traceUsers(TraceUsersEntity.builder()
                                                                                 // TODO
                                                                                 .build())
                                                                 .build());
    }

    return entity;
  }

}