package com.example.demo.dto.auth;

import com.example.demo.validation.StringLength;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull
    @StringLength(min = 4)
    private String password;
}