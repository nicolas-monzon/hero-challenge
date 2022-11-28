package com.core.hero.service;

import com.core.hero.entities.Hero;
import com.core.hero.errors.db.DeleteInstanceException;
import com.core.hero.errors.db.GetInstanceException;
import com.core.hero.errors.db.SaveInstanceException;
import com.core.hero.repositories.HeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Hero> findById(long id) {
        try {
            return this.heroRepository.findById(id);
        } catch (Exception e) {
            throw new GetInstanceException(Hero.class.getSimpleName());
        }
    }

    public List<Hero> findByNameContainingIgnoreCase(String sub) {
        try {
            return this.heroRepository.findByNameContainingIgnoreCase(sub);
        } catch (Exception e) {
            throw new GetInstanceException(Hero.class.getSimpleName());
        }
    }

    public void save(Hero hero) {
        try {
            this.heroRepository.save(hero);
        } catch (Exception e) {
            throw new SaveInstanceException(Hero.class.getSimpleName());
        }
    }

    public void delete(long id) {
        try {
            this.heroRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteInstanceException(Hero.class.getSimpleName());
        }
    }

}
