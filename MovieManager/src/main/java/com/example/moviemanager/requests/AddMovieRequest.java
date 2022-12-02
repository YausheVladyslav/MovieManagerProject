package com.example.moviemanager.requests;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Min(value = 1)
    @Max(value = 10)
    private int rating;
}
