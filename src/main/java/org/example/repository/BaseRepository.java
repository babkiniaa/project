package org.example.repository;

import org.example.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BaseRepository extends JpaRepository<Base, Integer> {
}
