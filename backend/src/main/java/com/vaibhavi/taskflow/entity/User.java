package com.vaibhavi.taskflow.entity;

import com.vaibhavi.taskflow.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
