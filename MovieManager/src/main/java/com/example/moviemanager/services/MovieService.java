package com.example.moviemanager.services;

import com.example.moviemanager.entities.MovieEntity;
import com.example.moviemanager.enums.Genre;
import com.example.moviemanager.exceptions.BadRequestException;
import com.example.moviemanager.repositories.MovieRepository;
import com.example.moviemanager.responses.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public void addMovie(String name, String genre, int year) {
        Optional<MovieEntity> moviesName = repository.findByName(name);
        if (moviesName.isPresent()) {
            throw new BadRequestException("This movie already added");
        }
        MovieEntity movie = new MovieEntity();
        movie.setName(name);
        movie.setGenre(Genre.valueOf(genre));
        movie.setYear(year);
        movie.setWatched(false);
        repository.save(movie);
    }

    public void watched(String movieId){
        Optional<MovieEntity> movieName = repository.findById(Long.parseLong(movieId));
        if (movieName.isPresent()){
            MovieEntity editMovie = movieName.get();
            editMovie.setWatched(!editMovie.isWatched());
            repository.save(editMovie);
        } else {
            throw new BadRequestException("Movie not found");
        }
    }

    public void editMovie(String movieId, String newName, String newGenre, int newYear) {
        Optional<MovieEntity> moviesName = repository.findById(Long.parseLong(movieId));
        if (moviesName.isPresent()) {
            MovieEntity modifiedMovie = moviesName.get();
            modifiedMovie.setName(newName);
            modifiedMovie.setGenre(Genre.valueOf(newGenre));
            modifiedMovie.setYear(newYear);
            repository.save(modifiedMovie);
        } else {
            throw new BadRequestException("Movie not found");
        }
    }

    public void deleteMovie(String movieId) {
        repository.deleteById(Long.parseLong(movieId));
    }

    public List<MovieResponse> movieList() {
        List<MovieEntity> moviesGenreList = repository.findAll();
        List<MovieResponse> response = new ArrayList<>();
        if (moviesGenreList.size() != 0) {
            for (MovieEntity movie : moviesGenreList) {
                response.add(new MovieResponse(
                        movie.getName(),
                        movie.getGenre().toString(),
                        movie.getYear(),
                        movie.getCreatedOn(),
                        movie.isWatched()));
            }
        } else {
            throw new BadRequestException("No saved movies");
        }
        return response;
    }

    public List<MovieResponse> moviesGenre(String genre) {
        List<MovieEntity> moviesGenreList = repository.findAllByGenre(Genre.valueOf(genre));
        List<MovieResponse> response = new ArrayList<>();
        if (moviesGenreList.size() != 0) {
            for (MovieEntity movie : moviesGenreList) {
                response.add(new MovieResponse(
                        movie.getName(),
                        movie.getGenre().toString(),
                        movie.getYear(),
                        movie.getCreatedOn(),
                        movie.isWatched()));
            }
        } else {
            throw new BadRequestException("No movies of this genre");
        }
        return response;
    }

    public MovieResponse movieRandomizer() {
        List<MovieEntity> movieList = repository.findAll();
        if (movieList.size() != 0) {
            Random random = new Random();
            int numberOfMovie = random.nextInt(movieList.size());
            return new MovieResponse(
                    movieList.get(numberOfMovie).getName(),
                    movieList.get(numberOfMovie).getGenre().toString(),
                    movieList.get(numberOfMovie).getYear(),
                    movieList.get(numberOfMovie).getCreatedOn(),
                    movieList.get(numberOfMovie).isWatched());
        } else {
            throw new BadRequestException("No saved movies");
        }
    }
}
