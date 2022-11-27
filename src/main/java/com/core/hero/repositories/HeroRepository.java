package com.core.hero.repositories;

import com.core.hero.entities.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeroRepository extends JpaRepository<Hero, Long> {

    List<Hero> findByNameContainingIgnoreCase(String sub);

}
