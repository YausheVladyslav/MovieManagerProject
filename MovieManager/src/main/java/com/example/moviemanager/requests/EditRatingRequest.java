package com.example.moviemanager.requests;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class EditRatingRequest {

    @Range(max = 10)
    private int rating;
}
