package com.core.hero.controller.payload;

import com.core.hero.enums.Power;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class HeroResponseTest {

    @Test
    @DisplayName("Should create instance of HeroResponse")
    public void shouldCreateInstanceOfHeroResponse() {
        assertDoesNotThrow(() -> new HeroResponse());
        final var response = new HeroResponse(1,
                "Java Developer",
                15000,
                100,
                10000,
                Power.INVULNERABILITY,
                new Date());
        assertDoesNotThrow(() -> {
            response.setId(2);
            response.setName("Spring Boot Developer");
            response.setStrength(16000);
            response.setSpeed(1000);
            response.setDurability(9000);
            response.setPower(Power.MUTATION);
            response.setBirthdate(new Date());
        });
        assertEquals(2, response.getId());
        assertEquals("Spring Boot Developer", response.getName());
        assertEquals(16000, response.getStrength());
        assertEquals(1000, response.getSpeed());
        assertEquals(9000, response.getDurability());
        assertEquals(Power.MUTATION, response.getPower());
        assertNotNull(response.getBirthdate());

    }

}
