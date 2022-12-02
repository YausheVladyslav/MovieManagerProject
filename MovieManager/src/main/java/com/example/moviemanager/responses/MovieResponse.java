package com.example.moviemanager.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MovieResponse {

    private String name;
    private String genre;
    private int year;
    private LocalDate createdOn;
    private boolean watched;
    private int rating;
}
