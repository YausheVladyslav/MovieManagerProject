package com.example.moviemanager.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class EditPasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String repeatNewPassword;
}
