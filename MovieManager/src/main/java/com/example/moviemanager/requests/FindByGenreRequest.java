package com.example.moviemanager.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class FindByGenreRequest {

    @NotBlank
    private String genre;
}
