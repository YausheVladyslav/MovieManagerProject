package com.example.moviemanager.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class AddMovieRequest {

    @NotBlank
    @Size(min = 2, max = 64)
    private String name;
    @NotBlank
    private String genre;
    private int year;
}
