package com.core.hero.converter;

import com.core.hero.enums.Power;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PowerConverterTest {

    private PowerConverter powerConverter;

    @BeforeEach
    public void beforeEach() {
        this.powerConverter = new PowerConverter();
    }

    @Test
    @DisplayName("Should convert to database column")
    public void shouldConvertToDatabaseColumn() {
        assertNull(this.powerConverter.convertToDatabaseColumn(null));

        assertEquals("MUT", this.powerConverter.convertToDatabaseColumn(Power.MUTATION));
        assertEquals("INV", this.powerConverter.convertToDatabaseColumn(Power.INVULNERABILITY));
        assertEquals("TLK", this.powerConverter.convertToDatabaseColumn(Power.TELEKINESIS));
    }

    @Test
    @DisplayName("Should convert to attribute")
    public void shouldConvertToEntityAttribute() {
        assertNull(this.powerConverter.convertToEntityAttribute(null));

        assertEquals(Power.MUTATION, this.powerConverter.convertToEntityAttribute("MUT"));
        assertEquals(Power.INVULNERABILITY, this.powerConverter.convertToEntityAttribute("INV"));
        assertEquals(Power.TELEKINESIS, this.powerConverter.convertToEntityAttribute("TLK"));

        assertThrows(IllegalArgumentException.class, () -> this.powerConverter.convertToEntityAttribute("ABC"));
    }

}
