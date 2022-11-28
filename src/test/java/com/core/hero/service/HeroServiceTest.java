package com.core.hero.service;

import com.core.hero.dto.HeroDto;
import com.core.hero.entities.Hero;
import com.core.hero.enums.Power;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    private List<Hero> getMockAllHeroes() {
        List<Hero> mockAllHeroes = new ArrayList<>();
        mockAllHeroes.add(
                new Hero(1, "Spiderman", 100, 101, 102, Power.MUTATION, new Date())
        );
        mockAllHeroes.add(
                new Hero(1, "Ironman", 500, 600, 1500, Power.MUTATION, new Date())
        );
        mockAllHeroes.add(
                new Hero(1, "Hulk", 1000, 90, 900, Power.MUTATION, new Date())
        );
        return mockAllHeroes;
    }

}
