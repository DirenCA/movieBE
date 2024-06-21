package de.htwberlin.webtech.moviediary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "watchlist_film",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<FilmEntry.Film> films = new HashSet<>();

    @OneToOne(mappedBy = "watchlist")
    private FilmUser filmUser;


    public Watchlist() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<FilmEntry.Film> getFilms() {
        return films;
    }

    public void setFilms(Set<FilmEntry.Film> films) {
        this.films = films;
    }

    public FilmUser getFilmUser() {
        return filmUser;
    }

    public void setFilmUser(FilmUser filmUser) {
        this.filmUser = filmUser;
    }
}
