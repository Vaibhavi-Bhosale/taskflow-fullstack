package com.vaibhavi.taskflow.dto;

import com.vaibhavi.taskflow.enums.Priority;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank
    private String title;

    private String description;
    private Priority priority;
}
