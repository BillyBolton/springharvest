package ca.billybolton.crud.service;

import ca.billybolton.common.models.dtos.BaseDTO;
import ca.billybolton.common.models.entities.BaseEntity;
import ca.billybolton.crud.mappers.CyclicMappingHandler;
import ca.billybolton.crud.mappers.IBaseModelMapper;
import ca.billybolton.crud.persistence.IBaseCrudRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
public abstract class AbstractBaseCrudService<D extends BaseDTO<K>, E extends BaseEntity<K>, K>
        implements IBaseService<D, K> {

    protected IBaseModelMapper<D, E, K> baseMapper;
    protected IBaseCrudRepository<E, K> baseRepository;

    protected AbstractBaseCrudService(IBaseModelMapper<D, E, K> baseMapper,
                                      IBaseCrudRepository<E, K> baseRepository) {
        this.baseMapper = baseMapper;
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
    public D findById(K id) {
        return entityToDto(baseRepository.findById(id).orElse(null));
    }

    @Override
    public List<D> findAllByIds(List<K> ids) {
        return entityToDto(baseRepository.findAllById(ids));
    }

    @Override
    public List<D> findAll() {
        List<E> entities = baseRepository.findAll();
        return entityToDto(entities);
    }

    @Transactional
    public D create(D dto) {
        beforeCreation(dto);
        E entity = dtoToEntity(dto);
        try {
            return entityToDto(baseRepository.save(entity));
        } catch (EntityExistsException e) {
            log.error("Entity already exists", e);
            throw e;
        }
    }

    @Transactional
    public List<D> create(List<D> dtos) {
        beforeCreation(dtos);
        return entityToDto(baseRepository.saveAll(dtoToEntity(dtos)));
    }

    @Transactional
    public D update(D dto) {
        return entityToDto(baseRepository.save(dtoToEntity(beforeUpdate(dto))));
    }

    @Transactional
    public List<D> update(List<D> source) {
        return source.stream().map(this::update).toList();
    }

    @Transactional
    public void deleteById(K id) {
        baseRepository.deleteById(id);
    }

    @Transactional
    public void deleteById(List<K> ids) {
        baseRepository.deleteAllById(ids);
    }


    protected D beforeCreation(D source) {
        scrubId(source);
        return source;
    }

    protected List<D> beforeCreation(List<D> source) {
        source.stream().forEach(this::beforeCreation);
        return source;
    }

    protected D beforeUpdate(D source) {
        validate(source);
        K id = source.getId();
        if (!existsById(id)) {
            throw new EntityNotFoundException();
        }

        return baseMapper.setDirtyFields(source, findById(id), new CyclicMappingHandler());
    }

    protected void validate(D dto) {
        // TODO: implement update validation
    }

    private void scrubId(D dto) {
        dto.setId(null);
    }

    protected D entityToDto(E entity) {
        return baseMapper.entityToDto(entity);
    }

    protected E dtoToEntity(D dto) {
        return baseMapper.dtoToEntity(dto);
    }

    protected List<D> entityToDto(List<E> entities) {
        return baseMapper.entityToDto(entities);
    }

    protected List<E> dtoToEntity(List<D> dtos) {
        return baseMapper.dtoToEntity(dtos);
    }


}