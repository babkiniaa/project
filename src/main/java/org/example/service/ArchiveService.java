package org.example.service;

import org.example.entity.ArchiveTask;
import org.example.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchiveService {
    private ArchiveRepository archiveRepository;

    @Autowired
    public void BaseService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    public List<ArchiveTask> getAllBase() {
        System.out.println("Получаем все данные");
        List<ArchiveTask> bases = (List<ArchiveTask>) archiveRepository.findAll();
        if (bases.size() > 0) {
            return bases;
        } else {
            return new ArrayList<ArchiveTask>();
        }
    }

    public ArchiveTask getBaseById(int id) {
        System.out.println("Получаем нужную запись");
        Optional<ArchiveTask> base = archiveRepository.findById(id);
        if (base.isPresent()) {
            return base.get();
        } else {
            System.out.println("запись не найдена");
            return new ArchiveTask();
        }
    }

    public ArchiveTask createOrUpdateBase(ArchiveTask archiveTask) {
        System.out.println("createOrUpdateUser");
        if (archiveTask.getId() == 0) {
            archiveTask = archiveRepository.save(archiveTask);
            return archiveTask;
        } else {
            Optional<ArchiveTask> baseOld = archiveRepository.findById(archiveTask.getId());
            if (baseOld.isPresent()) {
                ArchiveTask newBase = baseOld.get();
                newBase.setName(archiveTask.getName());
                newBase.setTime(archiveTask.getTime());
                newBase.setActive(archiveTask.getActive());
                newBase.setRating(archiveTask.getRating());

                archiveRepository.save(newBase);

                return newBase;
            } else {
                archiveTask = archiveRepository.save(archiveTask);
                return archiveTask;
            }
        }
    }

    public void deleteTaskArchiveById(int id){
        System.out.println("Удаление задания");
        Optional<ArchiveTask> base = archiveRepository.findById(id);
        if(base.isPresent()){
            archiveRepository.deleteById(id);
        }else{
            System.out.println("Такого задания нет");
        }
    }
}
