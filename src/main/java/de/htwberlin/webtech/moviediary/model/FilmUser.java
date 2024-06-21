package de.htwberlin.webtech.moviediary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class FilmUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String password;
    private String token; // Hinzufügen der Token-Eigenschaft

    @OneToOne
    private Watchlist watchlist;

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

    // Getter und Setter für die Token-Eigenschaft
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
