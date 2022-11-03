package com.example.moviemanager.controllers;

import com.example.moviemanager.entities.UserEntity;
import com.example.moviemanager.enums.Genre;
import com.example.moviemanager.requests.AddMovieRequest;
import com.example.moviemanager.requests.EditMovieRequest;
import com.example.moviemanager.responses.MovieResponse;
import com.example.moviemanager.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @PostMapping("/add")
    public ResponseEntity<Void> addMovie(@Valid
                                         @RequestBody AddMovieRequest request,
                                         @AuthenticationPrincipal UserEntity entity) {
        service.addMovie(
                request.getName(),
                request.getGenre(),
                request.getYear(),
                entity.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/edit")
    public ResponseEntity<Void> editMovie(
            @PathVariable long id,
            @Valid @RequestBody EditMovieRequest request,
            @AuthenticationPrincipal UserEntity user
    ) {
        service.editMovie(
                id,
                request.getNewName(),
                request.getGenre(),
                request.getYear(),
        user.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable long id,
            @AuthenticationPrincipal UserEntity user
            ) {
        service.deleteMovie(id, user.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<MovieResponse>> movieList(
            @AuthenticationPrincipal UserEntity user
    ) {
        return ResponseEntity.ok(service.movieList(user.getUserId()));
    }

    @GetMapping("/list/{genre}")
    public ResponseEntity<List<MovieResponse>> genreList(
            @PathVariable Genre genre,
            @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.moviesGenre(genre.toString(), user.getUserId()));
    }

    @GetMapping("/random")
    public ResponseEntity<MovieResponse> randomizer(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.movieRandomizer(user.getUserId()));
    }

    @PutMapping("{id}/watched")
    public ResponseEntity<Void> editWatched(
            @PathVariable long id,
            @AuthenticationPrincipal UserEntity user) {
        service.watched(id, user.getUserId());
        return ResponseEntity.ok().build();
    }
}
