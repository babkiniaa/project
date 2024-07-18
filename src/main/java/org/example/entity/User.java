package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Email(message = "Такого email не сущевствует")
    @NotEmpty(message = "Обязательное поле")
    @Column(name = "email", nullable = false)
    private String email;
    @NotEmpty(message = "Обязательное поле")
    @Column(name = "password", nullable = false)
    private String password;
    @Column(nullable = false)
    private String role = "ROLE_USERS";
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Base> bases;

}
