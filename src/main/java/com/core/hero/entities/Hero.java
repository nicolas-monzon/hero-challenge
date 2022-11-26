package com.core.hero.entities;

import com.core.hero.enums.Power;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(of = "name")
@Getter
@Setter
@Entity
@Table(name = "hero",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})
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

    @Column(name = "name", length = 64, nullable = false, updatable = false)
    private String name;

    @Column(name = "strength", nullable = false)
    private int strength;

    @Column(name = "speed", nullable = false)
    private int speed;

    @Column(name = "durability", nullable = false)
    private int durability;

    @Enumerated(EnumType.STRING)
    @Column(name = "power", nullable = false)
    private Power power;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthdate")
    private Date birthdate;
}