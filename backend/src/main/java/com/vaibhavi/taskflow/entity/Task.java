package com.vaibhavi.taskflow.entity;

import com.vaibhavi.taskflow.enums.Priority;
import com.vaibhavi.taskflow.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;


    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
