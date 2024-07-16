package org.example.service;

import org.example.entity.Base;
import org.example.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseService {
    @Autowired
    BaseRepository baseRepository;

    public List<Base> getAllBase(){
        System.out.println("Получаем все данные");
        List<Base> bases = (List<Base>) baseRepository.findAll();
        if(bases.size() > 0){
            return bases;
        }else{
            return new ArrayList<Base>();
        }
    }

}
