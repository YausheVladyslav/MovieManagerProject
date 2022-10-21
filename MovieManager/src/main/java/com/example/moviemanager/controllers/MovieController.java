package com.example.moviemanager.controllers;

import com.example.moviemanager.requests.AddMovieRequest;
import com.example.moviemanager.requests.DeleteMovieRequest;
import com.example.moviemanager.requests.EditMovieRequest;
import com.example.moviemanager.requests.FindByGenreRequest;
import com.example.moviemanager.responses.MovieResponse;
import com.example.moviemanager.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @PostMapping("/add")
    public ResponseEntity<Void> addMovie(@Valid @RequestBody AddMovieRequest request) {
        service.addMovie(request.getName(), request.getGenre(), request.getYear());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> editMovie(
            @PathVariable String id,
            @Valid @RequestBody EditMovieRequest request
    ) {
        service.editMovie(
                id,
                request.getNewName(),
                request.getGenre(),
                request.getYear());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        service.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/full-list")
    public ResponseEntity<List<MovieResponse>> movieList() {
        return ResponseEntity.ok(service.movieList());
    }

    @GetMapping("/genre-list")
    public ResponseEntity<List<MovieResponse>> genreList(
            @Valid @RequestBody FindByGenreRequest request) {
        return ResponseEntity.ok(service.moviesGenre(request.getGenre()));
    }

    @GetMapping("/random")
    public ResponseEntity<MovieResponse> randomizer() {
        return ResponseEntity.ok(service.movieRandomizer());
    }

    @PutMapping("/watched/{id}")
    public ResponseEntity<Void> editWatched(@PathVariable String id) {
        service.watched(id);
        return ResponseEntity.ok().build();
    }
}
