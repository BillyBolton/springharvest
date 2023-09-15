package dev.springharvest.search.mapper.transformers;

import dev.springharvest.search.model.entities.IEntityMetadata;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDaoImpl;
import dev.springharvest.search.persistence.ICriteriaSearchRepository;
import dev.springhavest.common.models.domains.DomainModel;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

@Slf4j
/**
 * This class is used to transform a Tuple that is returned from a TupleQuery in the CriteriaSearch repository, into
 * a concrete Entity.
 * @param <M> The highest parameterized type of data-transfer-object (DTO), business-object (BO), or Entity.
 * @see ICriteriaSearchRepository
 * @see AbstractCriteriaSearchDaoImpl
 */ public abstract class AbstractBaseTupleTransformer<M extends DomainModel> implements Function<Tuple, M> {

    protected IEntityMetadata<M> entityMetadata;

    @Autowired
    protected AbstractBaseTupleTransformer(IEntityMetadata<M> entityMetadata) {
        this.entityMetadata = entityMetadata;
    }


    /**
     * This method is used to scrub the alias of the TupleElement so that it can be mapped to the correct field in the
     * entity.
     *
     * @param alias The alias of the TupleElement.
     *
     * @return The scrubbed alias of the TupleElement.
     */
    protected String scrubAlias(String alias) {

        String pluralDomainName = entityMetadata.getDomainName(true);
        String singularDomainName = entityMetadata.getDomainName(false);


        if (StringUtils.contains(alias, pluralDomainName)) {
            alias = StringUtils.replace(alias, pluralDomainName, singularDomainName);
        }

        if (StringUtils.contains(alias, singularDomainName) && !StringUtils.startsWith(alias, singularDomainName)) {
            String prefix = StringUtils.substringBefore(alias, singularDomainName);
            alias = StringUtils.substringAfter(alias, prefix);
        }

        return alias;
    }

    /**
     * This method is used to get a new instance of the entity that is being transformed.
     *
     * @return A new instance of the entity that is being transformed.
     */
    // TODO: Investigate whether this method can be removed by using parameterized builders instead.
    protected abstract M getNewEntity();

    /**
     * This method is used to map the TupleElement to the domain model object.
     *
     * @param tuple The Tuple that is being transformed from.
     *
     * @return The domain model object that is being transformed to.
     */
    protected M mapTuple(Tuple tuple) {

        M entity = getNewEntity();

        for (TupleElement<?> element : tuple.getElements()) {
            String alias = element.getAlias();
            Object value = tuple.get(alias);

            alias = scrubAlias(alias);

            mapTupleElement(entity, alias, value);
        }

        return entity.isEmpty() ? null : entity;
    }

    /**
     * This method is used to map the TupleElement to the domain model object.
     *
     * @param entity The domain model object that is being transformed to.
     * @param alias  The alias of the TupleElement.
     * @param value  The value of the TupleElement.
     *
     * @see DomainModel
     */
    protected abstract void mapTupleElement(M entity, String alias, Object value);

    /**
     * This method is used to upsert the associated entities of the entity that is being transformed.
     *
     * @param entity The entity that is to upsert associated entities to.
     * @param tuple  The Tuple that is being transformed from.
     *
     * @see DomainModel
     */
    protected void upsertAssociatedEntities(M entity, Tuple tuple) {
        log.info("No associations to upsert for entity: {}", entity.getClass().getSimpleName());
    }

    @Override
    public M apply(Tuple tuple) {

        M entity = mapTuple(tuple);
        if (entity == null) {
            entity = getNewEntity();
        }
        upsertAssociatedEntities(entity, tuple);

        return entity;
    }


}
