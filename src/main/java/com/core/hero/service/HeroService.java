package com.core.hero.service;

import com.core.hero.dto.HeroDto;
import com.core.hero.entities.Hero;
import com.core.hero.errors.http.NotFoundException;
import com.core.hero.facade.ModelMapperService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class HeroService {

    private final HeroBaseService heroBaseService;
    private final ModelMapperService modelMapperService;

    public List<HeroDto> findAll() {
        return this.heroBaseService.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "heroes")
    public HeroDto findById(@NonNull final Long id) {
        final Optional<Hero> source = this.heroBaseService.findById(id);
        if(source.isEmpty()) {
            throw new NotFoundException("The hero was not found");
        }
        log.info(String.format("The hero %s was saved in the cache", source.get().getName()));
        return this.map(source.get());
    }

    public List<HeroDto> findWith(@NonNull final String sub) {
        final List<Hero> heroes = this.heroBaseService.findByNameContainingIgnoreCase(sub);
        if(heroes.isEmpty()) {
            throw new NotFoundException("The hero was not found");
        }
        return this.mapAll(heroes);
    }

    private HeroDto map(Hero hero) {
        return this.modelMapperService.map(hero, HeroDto.class);
    }

    private List<HeroDto> mapAll(List<Hero> heroes) {
        return this.modelMapperService.mapAll(heroes, HeroDto.class);
    }

}
