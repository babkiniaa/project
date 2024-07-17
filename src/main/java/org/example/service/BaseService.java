package org.example.service;

import org.example.Enums.Category;
import org.example.Enums.Repitable;
import org.example.entity.Base;
import org.example.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BaseService {
    private BaseRepository baseRepository;

    @Autowired
    public void BaseService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }


    public List<Base> getAllBase() {
        List<Base> bases = (List<Base>) baseRepository.findAll();
        if (bases.size() > 0) {
            return bases;
        } else {
            return new ArrayList<Base>();
        }
    }

    public Base getBaseById(int id) {
        Optional<Base> base = baseRepository.findById(id);
        if (base.isPresent()) {
            return base.get();
        } else {
            return new Base(0, " ", " ", null, true, 0, Category.OTHER, Repitable.NEVER);
        }
    }

    public Base createOrUpdateBase(Base base) {
        if (base.getId() == 0) {
            base = baseRepository.save(base);
            return base;
        } else {
            Optional<Base> baseOld = baseRepository.findById(base.getId());
            if (baseOld.isPresent()) {
                Base newBase = baseOld.get();
                newBase.setName(base.getName());
                newBase.setTime(base.getTime());
                newBase.setActive(base.getActive());
                newBase.setRating(base.getRating());

                baseRepository.save(newBase);

                return newBase;
            } else {
                base = baseRepository.save(base);
                return base;
            }
        }
    }

    public void deleteBaseById(int id) {
        Optional<Base> base = baseRepository.findById(id);
        if (base.isPresent()) {
            baseRepository.deleteById(id);
        } else {
            System.out.println("Такой базы нет");
        }
    }

    public List<Base> findByActive(Boolean active) {
        return baseRepository.findAllByActive(active);
    }

    public List<Base> findByTime(LocalDateTime time) {
        return baseRepository.findAllByTime(time);
    }

    public List<Base> findByCategory(Category category) {
        return baseRepository.findAllByCategory(category);
    }

    public List<Base> findByName(String search) {
        return baseRepository.findByNameOrDescription(search);
    }

    public List<Base> sortByRating() {
        return baseRepository.findAllOrderByRatingAsc();
    }

    public void nextTime(List<Base> bases) {
        for (Base bas : bases) {
            if (bas.getRepeatable() == Repitable.DAY) {
                bas.setTime(bas.getTime().plusDays(1));
            } else if (bas.getRepeatable() == Repitable.HOURS) {
                bas.setTime(bas.getTime().plusHours(1));
            } else if (bas.getRepeatable() == Repitable.WEEK) {
                bas.setTime(bas.getTime().plusWeeks(1));
            }
        }
    }

}
