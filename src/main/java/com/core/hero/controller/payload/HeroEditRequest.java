package com.core.hero.controller.payload;

import com.core.hero.enums.Power;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeroEditRequest {

    private Long id;
    private String name;
    private int strength;
    private int speed;
    private int durability;

    private Power power;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

}
