package com.demo.uploads.demo.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @Email
    private String email;
    @NotNull
    private String password;
}
