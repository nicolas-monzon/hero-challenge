package com.core.hero.service;

import com.core.hero.entities.Hero;
import com.core.hero.enums.Power;
import com.core.hero.errors.db.GetInstanceException;
import com.core.hero.repositories.HeroRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeroBaseServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HeroBaseService heroBaseService;

    @DisplayName("Should be get all heroes successfully")
    @Test
    public void shouldBeGetAllHeroes() {
        List<Hero> mockAllHeroes = this.getMockAllHeroes();
        when(heroRepository.findAll()).thenReturn(mockAllHeroes);

        List<Hero> heroes = heroBaseService.findAll();
        assertNotNull(heroes);
        assertEquals(mockAllHeroes.size(), heroes.size());

        Hero expected = mockAllHeroes.get(0);
        Hero actual = heroes.get(0);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSpeed(), actual.getSpeed());
        assertEquals(expected.getStrength(), actual.getStrength());
        assertEquals(expected.getDurability(), actual.getDurability());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPower(), actual.getPower());

        when(heroRepository.findAll()).thenReturn(List.of());

        heroes = heroBaseService.findAll();
        assertNotNull(heroes);
        assertEquals(0, heroes.size());
    }

    @DisplayName("Should be get all heroes with error")
    @Test
    public void shouldBeGetAllHeroesWithError() {
        when(heroRepository.findAll()).thenThrow(new GetInstanceException(Hero.class.getSimpleName()));

        assertThrows(GetInstanceException.class, () -> heroBaseService.findAll());
    }

    @DisplayName("Should be get hero by id successfully")
    @Test
    public void shouldBeGetHeroById() {
        Date date = new Date();
        Optional<Hero> mockHero = Optional.of(this.getMockHero(date));
        when(heroRepository.findById(eq(2L))).thenReturn(mockHero);

        Optional<Hero> heroes = heroBaseService.findById(2L);
        assertNotNull(heroes);
        assertTrue(heroes.isPresent());

        Hero expected = this.getMockHero(date);
        Hero actual = heroes.get();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSpeed(), actual.getSpeed());
        assertEquals(expected.getStrength(), actual.getStrength());
        assertEquals(expected.getDurability(), actual.getDurability());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPower(), actual.getPower());

        when(heroRepository.findById(eq(2L))).thenReturn(Optional.empty());

        heroes = heroBaseService.findById(2L);
        assertNotNull(heroes);
        assertTrue(heroes.isEmpty());
    }

    @DisplayName("Should be get hero by id with error")
    @Test
    public void shouldBeGetHeroByIdWithError() {
        when(heroRepository.findById(eq(2L))).thenThrow(new GetInstanceException(Hero.class.getSimpleName()));

        assertThrows(GetInstanceException.class, () -> heroBaseService.findById(2L));
    }

    private Hero getMockHero(final Date date) {
        return new Hero(2,
                "Fireman",
                100,
                101,
                102,
                Power.USE_OF_AN_ELEMENT,
                date);
    }

    private List<Hero> getMockAllHeroes() {
        List<Hero> mockAllHeroes = new ArrayList<>();
        mockAllHeroes.add(new Hero(2,
                "Fireman",
                100,
                101,
                102,
                Power.USE_OF_AN_ELEMENT,
                new Date()));
        mockAllHeroes.add(
                new Hero(1, "Ironman", 500, 600, 1500, Power.MUTATION, new Date())
        );
        mockAllHeroes.add(
                new Hero(3, "Hulk", 1000, 90, 900, Power.MUTATION, new Date())
        );
        return mockAllHeroes;
    }
}
