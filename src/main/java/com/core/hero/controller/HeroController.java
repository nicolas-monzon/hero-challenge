package com.core.hero.controller;

import com.core.hero.consts.Routes;
import com.core.hero.controller.payload.HeroResponse;
import com.core.hero.dto.HeroDto;
import com.core.hero.entities.Hero;
import com.core.hero.errors.http.NotFoundException;
import com.core.hero.facade.ModelMapperService;
import com.core.hero.service.HeroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(Routes.HERO_BASE)
@Validated
@RestController
public class HeroController {

    private final ModelMapperService modelMapperService;
    private final HeroService heroService;

    @GetMapping(Routes.HERO_GET_ALL)
    public ResponseEntity<List<HeroResponse>> all() {
        List<HeroResponse> heroes = this.modelMapperService.mapAll(this.heroService.findAll(), HeroResponse.class);
        return ResponseEntity.ok(heroes);
    }

    @GetMapping(Routes.HERO_GET_BY_ID)
    public ResponseEntity<?> heroById(
            @RequestParam @Valid Optional<@Positive Long> id,
            @RequestParam @Valid Optional<String> contains
    ) {
        if(id.isPresent()) {
            final HeroDto heroDto = this.heroService.findById(id.get());
            if(contains.isPresent() && !heroDto.getName().toLowerCase().contains(contains.get().toLowerCase())) {
                throw new NotFoundException("The hero was not found");
            }
            return ResponseEntity.ok(this.modelMapperService.map(heroDto, HeroResponse.class));
        }
        if(contains.isPresent()) {
            return ResponseEntity.ok(this.modelMapperService.mapAll(this.heroService.findWith(contains.get()),
                    HeroResponse.class));
        }

        List<HeroResponse> heroes = this.modelMapperService.mapAll(this.heroService.findAll(), HeroResponse.class);
        return ResponseEntity.ok(heroes);
    }

}