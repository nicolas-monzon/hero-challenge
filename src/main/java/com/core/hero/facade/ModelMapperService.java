package com.core.hero.facade;

import com.core.hero.errors.http.UnprocessableEntityException;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMapperService implements IModelMapperService {

    private static final String ERROR_MESSAGE = "Could not map an entity";

    private ModelMapper modelMapper;

    @PostConstruct
    protected void initialize() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public <S, D> D map(final S source, final D destination) {
        try {
            this.modelMapper.map(source, destination);
            return destination;
        } catch (IllegalArgumentException | ConfigurationException | MappingException e) {
            throw new UnprocessableEntityException(ERROR_MESSAGE);
        }
    }

    public <D, T> D map(final T entity, Class<D> outClass) {
        try {
            return this.modelMapper.map(entity, outClass);
        } catch (IllegalArgumentException | ConfigurationException | MappingException e) {
            throw new UnprocessableEntityException(ERROR_MESSAGE);
        }
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass) {
        try {
            return entityList.stream()
                    .map(entity -> map(entity, outClass))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | ConfigurationException | MappingException e) {
            throw new UnprocessableEntityException(ERROR_MESSAGE);
        }
    }
}
