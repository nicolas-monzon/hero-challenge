package com.core.hero.controller;

import com.core.hero.consts.Routes;
import com.core.hero.controller.payload.HeroResponse;
import com.core.hero.facade.ModelMapperService;
import com.core.hero.service.HeroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(Routes.HERO_BASE)
@Validated
@RestController
public class HeroController {

    private final ModelMapperService modelMapperService;
    private final HeroService heroService;

    @GetMapping(Routes.HERO_GET_ALL)
    public ResponseEntity<List<HeroResponse>> occasion_get() {
        List<HeroResponse> heroes = this.modelMapperService.mapAll(this.heroService.findAll(), HeroResponse.class);
        return ResponseEntity.ok(heroes);
    }

}
