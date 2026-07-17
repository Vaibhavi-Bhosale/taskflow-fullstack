package com.vaibhavi.taskflow.dto;

import com.vaibhavi.taskflow.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {

    private String name;
    private String email;
    private String password;

}
