package dev.springharvest.crud.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MappingTarget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A generic interface that can be used to map any domain model (data-transfer-object or Entity) to and from each
 * other.
 *
 * @param <D> the type parameter
 * @param <E> the type parameter
 * @param <K> the type parameter
 *
 * @author Billy Bolton
 * @since 1.0
 */
public interface IBaseModelMapper<D extends BaseDTO<K>, E extends BaseEntity<K>, K> {

    /**
     * This method is used to map a DTO object to an Entity.
     *
     * @param entity The Entity object that will be mapped from.
     *
     * @return The DTO object that will be mapped to.
     */
    @InheritInverseConfiguration
    D entityToDto(E entity);

    /**
     * This method is used to map a DTO object to an Entity.
     *
     * @param dto The DTO object that will be mapped from.
     *
     * @return The Entity object that will be mapped to.
     */
    E dtoToEntity(D dto);

    /**
     * This method is used to map a List of DTO objects to a List of Entities.
     *
     * @param entities The List of DTO objects that will be mapped from.
     *
     * @return The List of Entity objects that will be mapped to.
     */
    List<D> entityToDto(List<E> entities);

    /**
     * This method is used to map a List of DTO objects to a List of Entities.
     *
     * @param dtos The List of DTO objects that will be mapped from.
     *
     * @return The List of Entity objects that will be mapped to.
     */
    List<E> dtoToEntity(List<D> dtos);

    /**
     * * This method is used in conjunction in a PATCH request to refresh any attributes that are present in the already
     * persisted entity, * but omitted form the PATCH request.
     *
     * @param source  The DTO object that will be mapped from.
     * @param target  The DTO object that will be mapped to.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The DTO object that will be mapped to.
     */
    D setDirtyFields(D source, @MappingTarget D target, @Context CyclicMappingHandler context);

    /**
     * * This method is used in conjunction in a PATCH request to refresh any attributes that are present in the already
     * persisted entity, * but omitted form the PATCH request.
     *
     * @param source  The List of DTO objects that will be mapped from.
     * @param target  The List of DTO objects that will be mapped to.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The List of DTO objects that will be mapped to.
     */
    List<D> setDirtyFields(List<D> source, @MappingTarget List<D> target, @Context CyclicMappingHandler context);

    /**
     * This method maps a Map representation of a DTO object to an actual DTO object.
     *
     * @param source  The Map representation of a DTO object that will be mapped from.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The DTO object that will be mapped to.
     */
    D toDto(Map<String, String> source, @Context CyclicMappingHandler context);

    /**
     * This method maps a List of Map representations of DTO objects to a List of actual DTO objects.
     *
     * @param source  The List of Map representations of DTO objects that will be mapped from.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The List of DTO objects that will be mapped to.
     */
    List<D> toList(List<Map<String, String>> source, @Context CyclicMappingHandler context);

    /**
     * This method maps a Map representation of an Entity object to an actual Entity object.
     *
     * @param source  The Map representation of an Entity object that will be mapped from.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The Entity object that will be mapped to.
     */
    E toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

    /**
     * This method maps a List of Map representations of Entity objects to a List of actual Entity objects.
     *
     * @param source  The List of Map representations of Entity objects that will be mapped from.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The List of Entity objects that will be mapped to.
     */
    List<E> entityToList(ArrayList<Map<String, String>> source, @Context CyclicMappingHandler context);

    /**
     * This method maps a String representation of a List of Entities to a List of actual Entity objects.
     *
     * @param source  The String representation of a List of Entities that will be mapped from.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The List of Entity objects that will be mapped to.
     */
    default List<D> toList(String source, @Context CyclicMappingHandler context) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<D> target = objectMapper.readValue(source, new TypeReference<ArrayList<D>>() {
            });
            List<D> contextTarget =
                    context.getMappedInstance(objectMapper.readValue(source, new TypeReference<ArrayList<D>>() {
                            }),
                            List.class);
            if (contextTarget != null) {
                return contextTarget;
            }
            return target;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method maps a String representation of a List of Entities to a List of actual Entity objects.
     *
     * @param source  The String representation of a List of Entities that will be mapped from.
     * @param context The CyclicMappingHandler object that will be used to prevent infinite recursion.
     *
     * @return The List of Entity objects that will be mapped to.
     */
    default List<E> entityToList(String source, @Context CyclicMappingHandler context) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<E> target = objectMapper.readValue(source, new TypeReference<ArrayList<E>>() {
            });
            List<E> contextTarget =
                    context.getMappedInstance(objectMapper.readValue(source, new TypeReference<ArrayList<E>>() {
                            }),
                            List.class);
            if (contextTarget != null) {
                return contextTarget;
            }
            return target;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method capitalizes the first letter of any word within a String attribute of a DTO object.
     *
     * @param source The DTO object that be used for processing.
     */
    // TODO: Implement stop words from being ignored in capitalization
    @BeforeMapping
    default void capitalizeSourceValues(D source) {
        // Capitalize all string values in the source object
        if (source == null) {
            return;
        }
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value instanceof String) {
                    String[] words = ((String) value).split(" ");
                    for (int i = 0; i < words.length; i++) {
                        String word = words[i];
                        // TODO: if word is stop word, continue;
                        words[i] = StringUtils.capitalize(word);
                    }
                    field.set(source, String.join(" ", words));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}