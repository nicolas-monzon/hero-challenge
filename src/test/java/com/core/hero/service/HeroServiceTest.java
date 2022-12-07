package com.core.hero.service;

import com.core.hero.dto.HeroDto;
import com.core.hero.entities.Hero;
import com.core.hero.enums.Power;
import com.core.hero.errors.http.NotFoundException;
import com.core.hero.facade.ModelMapperService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @Mock
    private HeroBaseService heroBaseService;

    @InjectMocks
    private HeroService heroService;

    @SuppressWarnings("unused")
    @Mock
    private ModelMapperService modelMapperService;

    @DisplayName("Should be get all heroes successfully")
    @Test
    public void shouldBeGetAllHeroes() {
        List<Hero> mockAllHeroes = this.getMockAllHeroes();
        when(heroBaseService.findAll()).thenReturn(mockAllHeroes);

        HeroDto heroDto = new HeroDto(1,
                "Spiderman",
                100,
                101,
                102,
                Power.MUTATION,
                mockAllHeroes.get(0).getBirthdate());
        when(modelMapperService.map(mockAllHeroes.get(0), HeroDto.class)).thenReturn(heroDto);

        List<HeroDto> heroesDto = heroService.findAll();
        assertNotNull(heroesDto);
        assertNotEquals(0, heroesDto.size());
        assertEquals(mockAllHeroes.size(), heroesDto.size());

        Hero expected = mockAllHeroes.get(0);
        HeroDto actual = heroesDto.get(0);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSpeed(), actual.getSpeed());
        assertEquals(expected.getStrength(), actual.getStrength());
        assertEquals(expected.getDurability(), actual.getDurability());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPower(), actual.getPower());
    }

    @DisplayName("Should be get hero by id successfully")
    @Test
    public void shouldGetHeroById() {
        Date mockDate = new Date();
        Optional<Hero> mockHero = Optional.of(this.getMockHero(mockDate));
        when(heroBaseService.findById(eq(mockHero.get().getId()))).thenReturn(mockHero);
        when(modelMapperService.map(mockHero.get(), HeroDto.class)).thenReturn(this.getMockHeroDto(mockDate));

        HeroDto heroDto = heroService.findById(mockHero.get().getId());
        assertNotNull(heroDto);
        assertEquals("Spiderman", heroDto.getName());
        assertEquals(100, heroDto.getStrength());
        assertEquals(101, heroDto.getSpeed());
        assertEquals(102, heroDto.getDurability());
        assertEquals(Power.MUTATION, heroDto.getPower());
        assertNotNull(heroDto.getBirthdate());
    }

    @DisplayName("Should be get hero by id with error")
    @Test
    public void shouldGetHeroByIdWithError() {
        Optional<Hero> mockHero = Optional.empty();
        when(heroBaseService.findById(eq(1L))).thenReturn(mockHero);

        assertThrows(NotFoundException.class, () -> heroService.findById(1));
    }

    @DisplayName("Should be get hero by substring successfully")
    @Test
    public void shouldGetHeroBySubstring() {
        Date mockDate = new Date();
        List<Hero> mockHero = List.of(this.getMockHero(mockDate));
        when(heroBaseService.findByNameContainingIgnoreCase("der")).thenReturn(mockHero);
        when(modelMapperService.mapAll(mockHero, HeroDto.class)).thenReturn(List.of(this.getMockHeroDto(mockDate)));

        List<HeroDto> heroDto = heroService.findWith("der");
        assertNotNull(heroDto);
        assertEquals(1, heroDto.size());
        assertEquals("Spiderman", heroDto.get(0).getName());
        assertEquals(100, heroDto.get(0).getStrength());
        assertEquals(101, heroDto.get(0).getSpeed());
        assertEquals(102, heroDto.get(0).getDurability());
        assertEquals(Power.MUTATION, heroDto.get(0).getPower());
        assertNotNull(heroDto.get(0).getBirthdate());
    }

    @DisplayName("Should be get hero by substring with error")
    @Test
    public void shouldGetHeroByIdWithSubstring() {
        when(heroBaseService.findByNameContainingIgnoreCase(eq("der"))).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> heroService.findWith("der"));
    }

    @DisplayName("Should be update hero successfully")
    @Test
    public void shouldBeUpdateHero() {
        Hero mockHero = this.getMockAllHeroes().get(0);
        doNothing().when(this.heroBaseService).save(isA(Hero.class));
        when(this.heroBaseService.findById(eq(mockHero.getId()))).thenReturn(Optional.of(mockHero));

        this.heroService.update(new HeroDto(1,
                "Spiderman",
                101,
                102,
                104,
                Power.MUTATION,
                new Date())
        );

        verify(this.heroBaseService, times(1)).save(mockHero);
    }

    @DisplayName("Should be update hero with error")
    @Test
    public void shouldBeUpdateHeroWithError() {
        Hero mockHero = this.getMockAllHeroes().get(0);
        when(this.heroBaseService.findById(eq(mockHero.getId()))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.heroService.update(new HeroDto(1,
                "Spiderman",
                101,
                102,
                104,
                Power.MUTATION,
                new Date())
        ));
    }

    @DisplayName("Should be execute delete method for some hero successfully")
    @Test
    public void shouldBeDeleteHero() {
        this.heroService.delete(1);

        verify(this.heroBaseService, times(1)).delete(1);
    }

    private List<Hero> getMockAllHeroes() {
        List<Hero> mockAllHeroes = new ArrayList<>();
        mockAllHeroes.add(
                new Hero(1, "Spiderman", 100, 101, 102, Power.MUTATION, new Date())
        );
        mockAllHeroes.add(
                new Hero(2, "Ironman", 500, 600, 1500, Power.MUTATION, new Date())
        );
        mockAllHeroes.add(
                new Hero(3, "Hulk", 1000, 90, 900, Power.MUTATION, new Date())
        );
        return mockAllHeroes;
    }

    private Hero getMockHero(final Date date) {
        return new Hero(1,
                "Spiderman",
                100,
                101,
                102,
                Power.MUTATION,
                date);
    }

    private HeroDto getMockHeroDto(final Date date) {
        return new HeroDto(1,
                "Spiderman",
                100,
                101,
                102,
                Power.MUTATION,
                date);
    }

}
