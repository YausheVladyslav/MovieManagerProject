package com.example.moviemanager.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterAndDeleteResponse {

    private String action;
    private String firstName;
    private String secondName;
    private String nickname;
}
