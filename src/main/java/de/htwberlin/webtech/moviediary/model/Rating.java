package de.htwberlin.webtech.moviediary.model;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private FilmUser user;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private FilmEntry.Film film;

    public Rating(FilmUser user, FilmEntry.Film film, int rating) {
        this.user = user;
        this.film = film;
        this.rating = rating;
    }

    private int rating;

    public Rating() {}

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
