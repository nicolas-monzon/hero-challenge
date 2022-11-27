package com.core.hero.facade;

import java.util.Collection;
import java.util.List;

public interface IModelMapperService {

    <S, D> D map(S source, D destination);

    <D, T> D map(final T entity, Class<D> outClass);

    <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass);

}
