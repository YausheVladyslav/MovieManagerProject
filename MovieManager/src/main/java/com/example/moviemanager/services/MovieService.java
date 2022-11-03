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
    public void addMovie(String name, String genre, int year, Long id) {
        MovieEntity movie = new MovieEntity();
        UserEntity user = userRepository.findById(id).get();
        List<MovieEntity> movieList = user.getMovies();
        for (MovieEntity films : movieList) {
            if (films.getName().equals(name)) {
                throw new BadRequestException("This movie already added");
            }
        }
        movie.setName(name);
        movie.setGenre(Genre.valueOf(genre));
        movie.setYear(year);
        movie.setWatched(false);
        movieList.add(movie);
        user.setMovies(movieList);
        movieRepository.save(movie);
        userRepository.save(user);
    }

    public void watched(long movieId, long userId) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        List<MovieEntity> usersMovieList = userRepository.findById(userId).get().getMovies();
        for (MovieEntity movies : usersMovieList) {
            if (movieById.equals(movies)) {
                movies.setWatched(!movies.isWatched());
                movieRepository.save(movieById);
            }
        }
    }

    public void editMovie(long movieId, String newName, String newGenre, int newYear, long userId) {
        MovieEntity movieById = movieRepository.findById(movieId).get();
        List<MovieEntity> movies = userRepository.findById(userId).get().getMovies();
        for (MovieEntity movie : movies) {
            if (movie.equals(movieById)) {
                movieById.setName(newName);
                movieById.setGenre(Genre.valueOf(newGenre));
                movieById.setYear(newYear);
                movieRepository.save(movieById);
            }
        }
    }

    public void deleteMovie(long movieId, long userId) {
        MovieEntity deleteMovie = movieRepository.findById(movieId).get();
        List<MovieEntity> movies = userRepository.findById(userId).get().getMovies();
        for (MovieEntity movie : movies) {
            if (movie.equals(deleteMovie)) {
                movieRepository.deleteById(movieId);
            }
        }
    }

    public List<MovieResponse> movieList(Long userId) {
        UserEntity user = userRepository.findById(userId).get();
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

    public List<MovieResponse> moviesGenre(String genre, long userId) {
        UserEntity user = userRepository.findById(userId).get();
        List<MovieEntity> moviesList = user.getMovies();
        List<MovieResponse> response = new ArrayList<>();
        for (MovieEntity movie : moviesList) {
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

    public MovieResponse movieRandomizer(long userId) {
        List<MovieEntity> movieList = userRepository.findById(userId).get().getMovies();
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
