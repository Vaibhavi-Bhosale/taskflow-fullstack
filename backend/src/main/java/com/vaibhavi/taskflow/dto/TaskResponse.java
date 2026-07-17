package com.vaibhavi.taskflow.dto;

import com.vaibhavi.taskflow.enums.Priority;
import com.vaibhavi.taskflow.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

        private Long id;
        private String title;
        private String description;
        private Status status;
        private Priority priority;

        private UserResponse assignedTo;
    }

