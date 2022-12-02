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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addMovie(String name, String genre, int year, int rating, UserEntity user) {
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
        movie.setRating(rating);
        user.getMovies().add(movie);
        movieRepository.save(movie);
        userRepository.save(user);
    }

    @Transactional
    public void watched(long movieId, UserEntity user) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        if (movieById.getUser().getNickname().equals(user.getNickname())) {
            List<MovieEntity> movieList = user.getMovies();
            for (MovieEntity movie : movieList) {
                if (movie.getId() == movieId) {
                    movie.setWatched(!movie.isWatched());
                }
            }
            userRepository.save(user);
        }
    }

    @Transactional
    public void editRating(long movieId, int rating, UserEntity user) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        if (movieById.getUser().getNickname().equals(user.getNickname())) {
            List<MovieEntity> movieList = user.getMovies();
            for (MovieEntity movie : movieList) {
                if (movie.getId() == movieId) {
                    movie.setRating(rating);
                }
            }
            userRepository.save(user);
        }
    }

    @Transactional
    public void editMovie(long movieId, String newName, String newGenre, int newYear, int rating, UserEntity user) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        if (movieById.getUser().getNickname().equals(user.getNickname())) {
            List<MovieEntity> movieList = user.getMovies();
            for (MovieEntity movie : movieList) {
                if (movie.getId() == movieId) {
                    movie.setName(newName);
                    movie.setGenre(Genre.valueOf(newGenre));
                    movie.setYear(newYear);
                    movie.setWatched(false);
                    movie.setRating(rating);
                }
            }
            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteMovie(long movieId, UserEntity user) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        if (movieById.getUser().getNickname().equals(user.getNickname())) {
            List<MovieEntity> movieList = user.getMovies();
            for (int i = 0; i < movieList.size(); i++) {
                if (movieList.get(i).getId() == movieById.getId()) {
                    movieList.remove(i);
                }
            }
        }
        userRepository.save(user);
        movieRepository.deleteById(movieId);
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
                    movie.isWatched(),
                    movie.getRating()));
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
                        movie.isWatched(),
                        movie.getRating()));
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
                    movieList.get(numberOfMovie).isWatched(),
                    movieList.get(numberOfMovie).getRating());
        } else {
            throw new BadRequestException("No saved movies");
        }
    }

}
