package com.example.moviemanager.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private String url;
    private LocalDateTime time;


    public ErrorResponse(HttpStatus status, String message, String url) {
        this.message = message;
        this.url = url;
        this.status = status;
        this.time = LocalDateTime.now();
    }


}
