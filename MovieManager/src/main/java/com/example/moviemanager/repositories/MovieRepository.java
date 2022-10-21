package com.example.moviemanager.repositories;

import com.example.moviemanager.entities.MovieEntity;
import com.example.moviemanager.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    Optional<MovieEntity> findByName(String name);
    List<MovieEntity> findAllByGenre(Genre genre);
}
