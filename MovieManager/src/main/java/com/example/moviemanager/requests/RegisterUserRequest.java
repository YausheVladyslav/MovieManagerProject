package com.example.moviemanager.requests;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterUserRequest {

    @NotBlank
    @Size(min = 2, max = 32)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 32)
    private String secondName;
    @NotBlank
    @Size(min = 4, max = 64)
    private String nickname;
    @NotBlank
    @Size(min = 8, message = "Password should be at least 8 symbols")
    private String password;
    @NotBlank
    @Size(min = 8, message = "Password should be at least 8 symbols")
    private String repeatPassword;
    @NotBlank
    private String question;
    @NotBlank
    @Size(min = 2, max = 64)
    private String answer;
}
