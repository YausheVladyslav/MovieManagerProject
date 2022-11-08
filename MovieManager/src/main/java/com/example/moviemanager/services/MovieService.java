package com.example.moviemanager.services;

import com.example.moviemanager.entities.MovieEntity;
import com.example.moviemanager.entities.UserEntity;
import com.example.moviemanager.enums.Genre;
import com.example.moviemanager.exceptions.BadRequestException;
import com.example.moviemanager.repositories.MovieRepository;
import com.example.moviemanager.repositories.UserRepository;
import com.example.moviemanager.responses.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addMovie(String name, String genre, int year, UserEntity user) {
        MovieEntity movie = new MovieEntity();
        for (MovieEntity films : user.getMovies()) {
            if (films.getName().equals(name)) {
                throw new BadRequestException("This movie already added");
            }
        }
        movie.setName(name);
        movie.setGenre(Genre.valueOf(genre));
        movie.setYear(year);
        movie.setWatched(false);
        user.getMovies().add(movie);
        user.setMovies(user.getMovies());
        movieRepository.save(movie);
        userRepository.save(user);
    }

    public void watched(long movieId, UserEntity user) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        if (movieById.getUser().getNickname().equals(user.getNickname())) {
                movieById.setWatched(!movieById.isWatched());
                movieRepository.save(movieById);
        }
    }

    public void editMovie(long movieId, String newName, String newGenre, int newYear, UserEntity user) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
            if (movieById.getUser().getNickname().equals(user.getNickname())) {
                movieById.setName(newName);
                movieById.setGenre(Genre.valueOf(newGenre));
                movieById.setYear(newYear);
                movieRepository.save(movieById);
        }
    }

    public void deleteMovie(long movieId, UserEntity user) {
        MovieEntity deleteMovie = movieRepository.findById(movieId).get();
            if (deleteMovie.getUser().getNickname().equals(user.getNickname())) {
                movieRepository.deleteById(movieId);
        }
    }

    public List<MovieResponse> movieList(UserEntity user) {
        List<MovieEntity> movieList = user.getMovies();
        List<MovieResponse> response = new ArrayList<>();
        for (MovieEntity movie : movieList) {
            response.add(new MovieResponse(
                    movie.getName(),
                    movie.getGenre().toString(),
                    movie.getYear(),
                    movie.getCreatedOn(),
                    movie.isWatched()));
        }
        return response;
    }

    public List<MovieResponse> moviesGenre(String genre, UserEntity user) {
        List<MovieResponse> response = new ArrayList<>();
        for (MovieEntity movie : user.getMovies()) {
            if (movie.getGenre().toString().equals(genre)) {
                response.add(new MovieResponse(
                        movie.getName(),
                        movie.getGenre().toString(),
                        movie.getYear(),
                        movie.getCreatedOn(),
                        movie.isWatched()));
            }
        }
        return response;
    }

    public MovieResponse movieRandomizer(UserEntity user) {
        List<MovieEntity> movieList = user.getMovies();
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
