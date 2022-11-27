package com.core.hero.entities;

import com.core.hero.converter.PowerConverter;
import com.core.hero.enums.Power;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "HERO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"hero_name"})
        })
public class Hero {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column(name = "hero_name", length = 64, nullable = false, updatable = false)
    private String name;

    @Column(name = "strength", nullable = false)
    private int strength;

    @Column(name = "speed", nullable = false)
    private int speed;

    @Column(name = "durability", nullable = false)
    private int durability;

    @Convert(converter = PowerConverter.class)
    @Column(name = "power", nullable = false)
    private Power power;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthdate")
    private Date birthdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hero hero = (Hero) o;
        return id != null && Objects.equals(id, hero.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}