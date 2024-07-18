package org.example.service;

import org.example.Enums.Category;
import org.example.Enums.Repitable;
import org.example.entity.Base;
import org.example.entity.User;
import org.example.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public List<Base> getAllBase(User user) {
        List<Base> bases = (List<Base>) baseRepository.findAllByUser(user);
        if (bases.size() > 0) {
            return bases;
        } else {
            return new ArrayList<Base>();
        }
    }

    public Base getBaseById(int id, User user) {
        Base base = baseRepository.findByIdAndUser(id, user);
        return base;
    }
    @Transactional
    public Base createOrUpdateBase(Base base, User user) {
        if (base.getId() == 0) {
            base = baseRepository.save(base);
            return base;
        } else {
            Base baseOld = baseRepository.findByIdAndUser(base.getId(), user);
            baseOld.setId(base.getId());
            baseOld.setName(base.getName());
            baseOld.setTime(base.getTime());
            baseOld.setActive(base.getActive());
            baseOld.setRating(base.getRating());
            baseOld.setCategory(base.getCategory());
            baseOld.setRepeatable(base.getRepeatable());
            baseOld.setUser(user);
            baseRepository.save(baseOld);
            return baseOld;
        }
    }
    @Transactional
    public void deleteBaseById(int id, User user) {
        Base base = baseRepository.findByIdAndUser(id, user);
        baseRepository.deleteById(id);
    }

    public List<Base> findByActive(Boolean active, User user) {
        return baseRepository.findAllByActiveAndUser(active, user);
    }

    public List<Base> findByTime(LocalDateTime time, User user) {
        return baseRepository.findByTimeBeforeAndUser(time,user);
    }

    public List<Base> findByCategory(Category category, User user) {
        return baseRepository.findAllByCategoryAndUser(category, user);
    }

    public List<Base> findByName(String search, User user) {
        return baseRepository.findByNameOrDescriptionAndUser(search, user);
    }

    public List<Base> sortByRating(User user) {
        return baseRepository.findByUserOrderByRatingAsc(user);
    }
    @Transactional
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
