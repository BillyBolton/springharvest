package dev.springhavest.common.models.dtos;

/**
 * A generic interface for the base data-transfer-object, (DTO).
 *
 * @param <K> The type of the id (primary key) field
 *
 * @author Billy Bolton
 * @since 1.0
 */
public interface IBaseDTO<K> {

    /**
     * This method is used to get the id of the DTO.
     *
     * @return The id of the DTO.
     */
    K getId();

}
