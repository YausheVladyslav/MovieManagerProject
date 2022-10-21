package com.example.moviemanager.entities;

import com.example.moviemanager.enums.Genre;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private int year;
    @CreationTimestamp
    private LocalDate createdOn;
    @UpdateTimestamp
    private LocalDate updatedOn;
    private boolean watched;
}
