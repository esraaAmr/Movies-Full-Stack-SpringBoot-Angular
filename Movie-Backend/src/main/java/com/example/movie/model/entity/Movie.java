package com.example.movie.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 10)
    private String year;

    @Column(name = "imdb_id", unique = true, length = 20)
    private String imdbId;

    @Column(length = 10)
    private String rated;

    @Column(length = 50)
    private String released;

    @Column(length = 20)
    private String runtime;

    @Column(length = 500)
    private String genre;

    @Column(length = 255)
    private String director;

    @Column(length = 500)
    private String writer;

    @Column(length = 1000)
    private String actors;

    @Column(length = 2000)
    private String plot;

    @Column(length = 100)
    private String language;

    @Column(length = 100)
    private String country;

    @Column(length = 1000)
    private String awards;

    @Column(length = 500)
    private String poster;

    @Column(length = 10)
    private String metascore;

    @Column(name = "imdb_rating", length = 10)
    private String imdbRating;

    @Column(name = "imdb_votes", length = 20)
    private String imdbVotes;

    @Column(name = "box_office", length = 50)
    private String boxOffice;

    @Column(length = 255)
    private String production;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Rating> ratings;
}
