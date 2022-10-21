package com.example.moviemanager.requests;

import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Getter
public class AddMovieRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String genre;
    private String genreSecond;
    private String genreThird;
    private int year;
}
