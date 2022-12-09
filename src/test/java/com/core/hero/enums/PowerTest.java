package com.core.hero.enums;

import com.core.hero.errors.PowerNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class PowerTest {

    @TestFactory
    public Collection<DynamicTest> shouldCheckValues() {
        final String[] INVALID_VALUES = { "cloning", "laser_beam", "invisibility", "to_fly" };

        var list = Arrays.stream(Power.values()).map(Power::name).map(power -> DynamicTest.dynamicTest(
                "Should exists: " + power,
                () -> {
                    Power actual = Power.getByName(power);
                    assertNotNull(actual);
                })).collect(Collectors.toList());

        var list2 = Arrays.stream(INVALID_VALUES).map(power -> DynamicTest.dynamicTest(
                "Should not exists: " + power,
                () -> assertThrows(PowerNotFoundException.class, () -> Power.getByName(power))))
                .collect(Collectors.toList());

        final Collection<DynamicTest> collection = new ArrayList<>();

        collection.addAll(list);
        collection.addAll(list2);

        return collection;
    }

    @TestFactory
    public Collection<DynamicTest> shouldCheckExistsValues() {
        String[] INVALID_VALUES = { "cloning", "laser_beam", "invisibility", "to_fly" };

        List<DynamicTest> list = Arrays.stream(Power.values()).map(Power::name).map(power -> DynamicTest.dynamicTest(
                "Should exists: " + power,
                () -> assertTrue(Power.existsName(power)))).collect(Collectors.toList());

        List<DynamicTest> list2 = Arrays.stream(INVALID_VALUES).map(power -> DynamicTest.dynamicTest(
                        "Should not exists: " + power,
                        () -> assertFalse(Power.existsName(power))))
                .collect(Collectors.toList());

        Collection<DynamicTest> collection = new ArrayList<>();

        collection.addAll(list);
        collection.addAll(list2);

        return collection;
    }

    @Test
    @DisplayName("Should convert to json value")
    public void shouldConvertToJsonValue() {
        assertEquals("mutation", Power.MUTATION.toValue());
        assertEquals("power_override", Power.POWER_OVERRIDE.toValue());
        assertEquals("invulnerability", Power.INVULNERABILITY.toValue());
    }

}
