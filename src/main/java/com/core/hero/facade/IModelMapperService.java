package com.core.hero.facade;

import java.util.Collection;
import java.util.List;

public interface IModelMapperService {

    /**
     * This method map an entity of type S, to type T, with the values in all field if this is possible.
     * @param source entity from
     * @param destination entity type to
     * @return new entity of type to
     * @param <S> the key type
     * @param <D> the value type
     */
    <S, D> D map(S source, D destination);

    /**
     * This method map an entity of type T, to type D, with the values in all field if this is possible.
     * @param entity entity from
     * @param outClass entity class to
     * @return  new entity of type to
     * @param <D> the key type
     * @param <T> the value type
     */
    <D, T> D map(final T entity, Class<D> outClass);

    /**
     * This method map all entities of type T, to type D, with the values in all field if this is possible.
     * @param entityList entities from
     * @param outClass entities class to
     * @return list of entities with type to
     * @param <D> the key type
     * @param <T> the value type
     */
    <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass);

}
