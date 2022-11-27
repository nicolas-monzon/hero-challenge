package com.core.hero.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
