package dev.springharvest.search.mappers.transformers;

import jakarta.persistence.Tuple;
import java.util.function.Function;

/**
 * This interface contracts the methods that are used to transform a Tuple object to a model object.
 *
 * @param <M> The highest parameterized type of data-transfer-object (DTO), business-object (BO), or Entity.
 */
public interface IBaseTupleTransformer<M> extends Function<Tuple, M> {

}
