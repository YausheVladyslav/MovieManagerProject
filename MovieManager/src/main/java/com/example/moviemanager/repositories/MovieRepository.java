package com.example.moviemanager.repositories;

import com.example.moviemanager.entities.MovieEntity;
import com.example.moviemanager.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    MovieEntity findByName(String name);

    List<MovieEntity> findAllByGenre(Genre genre);
}
