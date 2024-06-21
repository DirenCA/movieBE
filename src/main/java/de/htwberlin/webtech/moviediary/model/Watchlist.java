package de.htwberlin.webtech.moviediary.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "watchlist", fetch = FetchType.EAGER)
    private List<FilmEntry.Film> films;

    public Watchlist() {
        this.films = new ArrayList<>(); // Initialize the films list
    }

    public Watchlist(List<FilmEntry.Film> films) {
        this.films = films;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FilmEntry.Film> getFilms() {
        return films;
    }

    public void setFilms(List<FilmEntry.Film> films) {
        this.films = films;
    }

    public void addMovie(FilmEntry.Film film) {
    }
}
