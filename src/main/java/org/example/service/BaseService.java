package org.example.service;

import org.example.Category;
import org.example.entity.Base;
import org.example.entity.User;
import org.example.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BaseService {
    private BaseRepository baseRepository;
    @Autowired
    public void BaseService(BaseRepository baseRepository){
        this.baseRepository = baseRepository;
    }


    public List<Base> getAllBase() {
        System.out.println("Получаем все данные");
        List<Base> bases = (List<Base>) baseRepository.findAll();
        if (bases.size() > 0) {
            return bases;
        } else {
            return new ArrayList<Base>();
        }
    }

    public Base getBaseById(int id) {
        System.out.println("Получаем нужную запись");
        Optional<Base> base = baseRepository.findById(id);
        if (base.isPresent()) {
            return base.get();
        } else {
            System.out.println("запись не найдена");
            return new Base(0, " ", " ", null,true,0, Category.OTHER);
        }
    }

    public Base createOrUpdateBase(Base base) {
        System.out.println("createOrUpdateUser");
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
        System.out.println("Удаление базы");
        Optional<Base> base = baseRepository.findById(id);
        if (base.isPresent()) {
            baseRepository.deleteById(id);
        } else {
            System.out.println("Такой базы нет");
        }
    }

    public List<Base> findByActive(Boolean active){
        return baseRepository.findAllByActive(active);
    }

    public List<Base> findByTime(LocalDateTime time){
        return baseRepository.findAllByTime(time);
    }

    public List<Base> findByName(String search){
        return baseRepository.findByNameOrDescription(search);
    }
}
