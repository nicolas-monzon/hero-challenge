package com.core.hero.controller.payload;

import com.core.hero.enums.Power;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HeroResponse {

    private long id;
    private String name;
    private int strength;
    private int speed;
    private int durability;

    private Power power;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

}
