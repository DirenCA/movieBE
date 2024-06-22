package de.htwberlin.webtech.moviediary.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FilmUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String password;
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchlist_id", referencedColumnName = "id")
    private Watchlist watchlist;

    @OneToMany(mappedBy = "user")
    private Set<Rating> ratings = new HashSet<>();

    public FilmUser() {
    }

    public FilmUser(String userName, String password, Watchlist watchlist) {
        this.userName = userName;
        this.password = password;
        this.watchlist = watchlist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }
}
