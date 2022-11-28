package com.core.hero.controller.payload;

import com.core.hero.controller.validation.PowerValidator;
import com.core.hero.enums.Power;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeroEditRequest {

    @Positive
    @NotNull
    private Long id;

    @Size(max = 64)
    @NotNull
    private String name;

    @PositiveOrZero
    @NotNull
    private Integer strength;

    @PositiveOrZero
    @NotNull
    private Integer speed;

    @PositiveOrZero
    @NotNull
    private Integer durability;

    @NotNull
    @PowerValidator
    private String power;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date birthdate;

}
