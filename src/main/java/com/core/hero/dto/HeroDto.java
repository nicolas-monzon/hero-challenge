package com.core.hero.dto;

import com.core.hero.enums.Power;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HeroDto {

    private String name;
    private int strength;
    private int speed;
    private int durability;
    private Power power;
    private Date birthdate;

}
