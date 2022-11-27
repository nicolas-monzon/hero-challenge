package com.core.hero.service;

import com.core.hero.entities.Hero;
import com.core.hero.errors.db.GetInstanceException;
import com.core.hero.repositories.HeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HeroBaseService {

    private final HeroRepository heroRepository;

    public List<Hero> findAll() {
        try {
            return this.heroRepository.findAll();
        } catch (Exception e) {
            throw new GetInstanceException(Hero.class.getSimpleName());
        }
    }

}
