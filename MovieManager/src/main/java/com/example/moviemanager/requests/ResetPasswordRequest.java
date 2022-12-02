package com.example.moviemanager.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordRequest {

    private String nickname;
    private String answer;
    private String password;
    private String repeatPassword;
}
