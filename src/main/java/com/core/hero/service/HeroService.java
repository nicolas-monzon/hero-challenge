package com.core.hero.service;

import com.core.hero.dto.HeroDto;
import com.core.hero.entities.Hero;
import com.core.hero.errors.http.NotFoundException;
import com.core.hero.facade.ModelMapperService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HeroService {

    private final HeroBaseService heroBaseService;
    private final ModelMapperService modelMapperService;


    public List<HeroDto> findAll() {
        return this.heroBaseService.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    public HeroDto findById(@NonNull final Long id) {
        final Optional<Hero> source = this.heroBaseService.findById(id);
        if(source.isEmpty()) {
            throw new NotFoundException("The hero was not found");
        }
        return this.map(source.get());
    }

    private HeroDto map(Hero hero) {
        return this.modelMapperService.map(hero, HeroDto.class);
    }

}
