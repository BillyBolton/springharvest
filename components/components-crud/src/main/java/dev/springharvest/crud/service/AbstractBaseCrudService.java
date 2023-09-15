package dev.springharvest.crud.service;

import dev.springharvest.crud.persistence.IBaseCrudRepository;
import dev.springhavest.common.models.entities.BaseEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractBaseCrudService<E extends BaseEntity<K>, K extends Serializable>
        implements IBaseService<E, K> {

    protected IBaseCrudRepository<E, K> baseRepository;

    protected AbstractBaseCrudService(IBaseCrudRepository<E, K> baseRepository) {
        this.baseRepository = baseRepository;
    }


    protected Class<E> getClazz() {
        ParameterizedType paramType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) paramType.getActualTypeArguments()[1];
    }

    @Override
    public long count() {
        return baseRepository.count();
    }

    @Override
    public boolean existsById(K id) {
        return baseRepository.existsById(id);
    }

    @Override
    public Optional<E> findById(K id) {
        return baseRepository.findById(id);
    }

    @Override
    public List<E> findAllByIds(List<K> ids) {
        return baseRepository.findAllById(ids);
    }

    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }

    @Transactional
    public E create(@Valid E entity) {
        entity = beforeCreation(entity);
        try {
            return baseRepository.save(entity);
        } catch (EntityExistsException e) {
            log.error("Entity already exists", e);
            throw e;
        }
    }

    @Transactional
    public List<E> create(@Valid List<E> entities) {
        return baseRepository.saveAll(beforeCreation(entities));
    }

    @Transactional
    public E update(@Valid E entity) {
        return baseRepository.save(beforeUpdate(entity));
    }

    @Transactional
    public List<E> update(@Valid List<E> entities) {
        return entities.stream().map(this::update).toList();
    }

    @Transactional
    public void deleteById(K id) {
        baseRepository.deleteById(id);
    }

    @Transactional
    public void deleteById(List<K> ids) {
        baseRepository.deleteAllById(ids);
    }

    protected E beforeCreation(E entity) {
        entity.setId(null);
        return entity;
    }

    protected List<E> beforeCreation(List<E> source) {
        source.forEach(this::beforeCreation);
        return source;
    }

    protected E beforeUpdate(E source) {
        validate(source);
        K id = source.getId();
        if (!existsById(id)) {
            throw new EntityNotFoundException();
        }
        return source;
    }

    protected void validate(E entity) {
        // TODO: implement update validation
    }


}