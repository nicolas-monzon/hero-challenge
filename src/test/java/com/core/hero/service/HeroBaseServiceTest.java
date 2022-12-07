package com.core.hero.service;

import com.core.hero.entities.Hero;
import com.core.hero.enums.Power;
import com.core.hero.errors.db.DeleteInstanceException;
import com.core.hero.errors.db.GetInstanceException;
import com.core.hero.errors.db.SaveInstanceException;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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
        when(heroRepository.findAll()).thenThrow(new RuntimeException());

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
        when(heroRepository.findById(eq(2L))).thenThrow(new RuntimeException());

        assertThrows(GetInstanceException.class, () -> heroBaseService.findById(2L));
    }

    @DisplayName("Should be get hero by substring successfully")
    @Test
    public void shouldBeGetHeroBySubstring() {
        Date date = new Date();
        List<Hero> mockHero = List.of(this.getMockHero(date));
        when(heroRepository.findByNameContainingIgnoreCase(eq("man"))).thenReturn(mockHero);

        List<Hero> heroes = heroBaseService.findByNameContainingIgnoreCase("man");
        assertNotNull(heroes);
        assertEquals(1, heroes.size());

        Hero expected = this.getMockHero(date);
        Hero actual = heroes.get(0);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSpeed(), actual.getSpeed());
        assertEquals(expected.getStrength(), actual.getStrength());
        assertEquals(expected.getDurability(), actual.getDurability());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPower(), actual.getPower());

        when(heroRepository.findByNameContainingIgnoreCase(eq("man"))).thenReturn(List.of());

        heroes = heroBaseService.findByNameContainingIgnoreCase("man");
        assertNotNull(heroes);
        assertTrue(heroes.isEmpty());
    }

    @DisplayName("Should be get hero by substring with error")
    @Test
    public void shouldBeGetHeroBySubstringWithError() {
        when(heroRepository.findByNameContainingIgnoreCase(eq("man")))
                .thenThrow(new RuntimeException());

        assertThrows(GetInstanceException.class, () -> heroBaseService.findByNameContainingIgnoreCase("man"));
    }

    @DisplayName("Should be save hero successfully")
    @Test
    public void shouldBeSaveHero() {
        Hero mockHero = this.getMockAllHeroes().get(0);

        this.heroBaseService.save(mockHero);

        verify(this.heroRepository, times(1)).save(mockHero);
    }

    @DisplayName("Should be save hero with error")
    @Test
    public void shouldBeSaveHeroWithError() {
        Hero mockHero = this.getMockAllHeroes().get(0);
        when(this.heroRepository.save(eq(mockHero))).thenThrow(new RuntimeException());

        assertThrows(SaveInstanceException.class, () -> this.heroBaseService.save(new Hero(1,
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
        this.heroBaseService.delete(1);

        verify(this.heroRepository, times(1)).deleteById(1);
    }

    @DisplayName("Should be execute delete method for some hero with error")
    @Test
    public void shouldBeDeleteHeroWithError() {
        doThrow(new RuntimeException()).when(this.heroRepository).deleteById(eq(1));

        assertThrows(DeleteInstanceException.class, () -> this.heroBaseService.delete(1));
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
