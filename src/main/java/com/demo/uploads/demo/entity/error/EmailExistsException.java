package com.demo.uploads.demo.entity.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailExistsException extends RuntimeException {
    String email;
}
