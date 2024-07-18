package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Enums.Category;
import org.example.Enums.Repitable;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Base")
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Обязательное поле")
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @NotNull(message = "Обязательное поле")
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "rating")
    private int rating = 0;
    @Column(name = "category")
    private Category category;
    @Column(name = "repeatable")
    private Repitable repeatable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
