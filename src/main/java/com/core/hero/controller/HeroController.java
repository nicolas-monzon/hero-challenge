package com.core.hero.controller;

import com.core.hero.annotations.Timed;
import com.core.hero.consts.Routes;
import com.core.hero.controller.payload.Empty;
import com.core.hero.controller.payload.HeroEditRequest;
import com.core.hero.controller.payload.HeroResponse;
import com.core.hero.dto.HeroDto;
import com.core.hero.enums.Power;
import com.core.hero.errors.http.NotFoundException;
import com.core.hero.facade.ModelMapperService;
import com.core.hero.service.HeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Timed
@Slf4j
@RequiredArgsConstructor
@RequestMapping(Routes.HERO_BASE)
@Validated
@RestController
@Tag(name = "hero", description = "Hero API")
public class HeroController {

    private final ModelMapperService modelMapperService;
    private final HeroService heroService;

    @Operation(summary = "Get all heroes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the heroes",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HeroResponse.class))})})
    @GetMapping(Routes.HERO_GET_ALL)
    public ResponseEntity<List<HeroResponse>> all() {
        List<HeroResponse> heroes = this.modelMapperService.mapAll(this.heroService.findAll(), HeroResponse.class);
        return ResponseEntity.ok(heroes);
    }

    @Operation(summary = "Search hero with pattern or search by id or both")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the heroes",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HeroResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Hero not found", content = @Content)})
    @GetMapping(Routes.HERO_GET)
    public ResponseEntity<?> search(
            @RequestParam @Valid Optional<@Positive Long> id,
            @RequestParam @Valid Optional<String> contains
    ) {
        if (id.isPresent()) {
            final HeroDto heroDto = this.heroService.findById(id.get());
            if (contains.isPresent() && !heroDto.getName().toLowerCase().contains(contains.get().toLowerCase())) {
                throw new NotFoundException("The hero was not found");
            }
            return ResponseEntity.ok(this.modelMapperService.map(heroDto, HeroResponse.class));
        }

        List<HeroResponse> heroes = contains.isPresent() ?
                this.modelMapperService.mapAll(this.heroService.findWith(contains.get()), HeroResponse.class)
                :
                this.modelMapperService.mapAll(this.heroService.findAll(), HeroResponse.class);
        return ResponseEntity.ok(heroes);
    }

    @Operation(summary = "Update some hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the hero",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HeroResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid field supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Hero not found", content = @Content)})
    @PutMapping(Routes.HERO_UPDATE)
    public ResponseEntity<HeroResponse> update(@Valid @RequestBody HeroEditRequest request) {
        final HeroDto heroDto = new HeroDto(request.getId(),
                request.getName(),
                request.getStrength(),
                request.getSpeed(),
                request.getDurability(),
                Power.getByName(request.getPower()),
                request.getBirthdate());
        this.heroService.update(heroDto);
        return ResponseEntity.ok(this.modelMapperService.map(request, HeroResponse.class));
    }

    @Operation(summary = "Delete some hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the hero",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HeroResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Hero not found", content = @Content)})
    @DeleteMapping(Routes.HERO_DELETE)
    public ResponseEntity<?> delete(@PathVariable @Valid @NotNull Long id) {
        this.heroService.delete(id);
        return ResponseEntity.ok(new Empty());
    }

}
