package com.vaibhavi.taskflow.dto;

import com.vaibhavi.taskflow.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateTaskStatusRequest {

    @NotNull
    private Status status;
}
