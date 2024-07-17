package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Enums.Category;
import org.example.Enums.Repitable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ArchiveTask")
public class ArchiveTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "rating")
    private int rating;
    @Column(name = "category")
    private Category category;
    @Column(name = "repitable")
    private Repitable repitable;
}
