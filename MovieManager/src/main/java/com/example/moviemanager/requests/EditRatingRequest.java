package com.example.moviemanager.requests;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class EditRatingRequest {

    @Min(value = 1)
    @Max(value = 10)
    private int rating;
}
