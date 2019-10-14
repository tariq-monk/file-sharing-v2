package com.demo.uploads.demo.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class FileShareRequestDto {

    @NotNull
    private String fileId;

    @Email
    private String email;
}
