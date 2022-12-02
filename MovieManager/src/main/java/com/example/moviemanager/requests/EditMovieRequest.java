package com.example.moviemanager.requests;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
public class EditMovieRequest {

    @NotBlank
    private String newName;
    @NotBlank
    private String genre;
    private int year;
    @Range(max = 10)
    private int rating;
}
