package org.example.repository;

import org.example.Enums.Category;
import org.example.entity.Base;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaseRepository extends JpaRepository<Base, Integer> {
    Base findByIdAndUser(Integer id, User user);
    List<Base> findAllByActiveAndUser(Boolean active, User user);
    List<Base> findAllByUser(User user);
    List<Base> findByUserAndName(User user, String email);
    List<Base> findAllByTimeAndUser(LocalDateTime time, User user);
    List<Base> findAllByCategoryAndUser(Category category, User user);
    @Query(value = "select u from Base u where (u.name = %?1% or u.description = %?1%) and u.user = ?2")
    List<Base> findByNameOrDescriptionAndUser(String search, User user);
    List<Base> findByUserOrderByRatingAsc(User user);
}
