package org.example.repository;

import org.example.entity.ArchiveTask;
import org.example.entity.Base;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<ArchiveTask, Integer> {
    List<ArchiveTask> findAllByUser(User user);
    ArchiveTask findByIdAndUser(Integer id, User user);
    @Query(value = "select u from ArchiveTask u where (u.name = %?1% or u.description = %?1%) and u.user = ?2")
    List<Base> findByNameOrDescriptionAndUser(String search, User user);
}
