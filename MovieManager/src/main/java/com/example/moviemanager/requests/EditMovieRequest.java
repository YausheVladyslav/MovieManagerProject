package com.example.moviemanager.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
@Getter
public class EditMovieRequest {

    @NotBlank
    private String newName;
    @NotBlank
    private String genre;
    private int year;
}
