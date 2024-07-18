package org.example.service;

import org.example.entity.ArchiveTask;
import org.example.entity.User;
import org.example.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchiveService {
    private ArchiveRepository archiveRepository;

    @Autowired
    public void ArchiveService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @Transactional(readOnly = true)
    public List<ArchiveTask> getAllArchive(User user) {
        List<ArchiveTask> bases = (List<ArchiveTask>) archiveRepository.findAllByUser(user);
        if (bases.size() > 0) {
            return bases;
        } else {
            return new ArrayList<ArchiveTask>();
        }
    }

    @Transactional(readOnly = true)
    public ArchiveTask getArchiveById(int id, User user) {
        return archiveRepository.findByIdAndUser(id, user);
    }

    @Transactional
    public ArchiveTask createOrUpdateArchive(ArchiveTask archiveTask, User user) {
        if (archiveTask.getId() == 0) {
            archiveTask = archiveRepository.save(archiveTask);
            return archiveTask;
        } else {
            ArchiveTask baseOld = archiveRepository.findByIdAndUser(archiveTask.getId(), user);
            baseOld.setName(archiveTask.getName());
            baseOld.setTime(archiveTask.getTime());
            baseOld.setActive(archiveTask.getActive());
            baseOld.setRating(archiveTask.getRating());
            baseOld.setCategory(archiveTask.getCategory());
            baseOld.setRepeatable(archiveTask.getRepeatable());
            baseOld.setUser(archiveTask.getUser());
            archiveRepository.save(baseOld);
            return baseOld;
        }
    }

    @Transactional
    public void deleteTaskArchiveById(int id,User user) {
        ArchiveTask base = archiveRepository.findByIdAndUser(id, user);
    }
}
