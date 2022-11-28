package com.core.hero.enums;

import com.core.hero.errors.PowerNotFoundException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Locale;

@AllArgsConstructor
@Getter
public enum Power {

    TELEKINESIS("TLK"),
    INVULNERABILITY("IMV"),
    TELEPATHY("TLP"),
    CLAIRVOYANCE("CLR"),
    MULTIPLICATION("MTP"),
    MUTATION("MUT"),
    POWER_OVERRIDE("POV"),
    TRANSFORMATION("TRM"),
    RESIZE("RES"),
    USE_OF_AN_ELEMENT("ELM");

    private final String code;

    public static Power getByName(@NonNull final String name) {
        return Arrays.stream(Power.values()).filter(power -> power.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(PowerNotFoundException::new);
    }

    public static boolean existsName(@NonNull final String name) {
        return Arrays.stream(Power.values()).anyMatch(power -> power.name().equalsIgnoreCase(name));
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
