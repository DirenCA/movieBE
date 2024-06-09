package de.htwberlin.webtech.moviediary.service;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.Watchlist;
import de.htwberlin.webtech.moviediary.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {

    @Autowired
    WatchlistRepository repo;

    public Watchlist saveWatchlist(Watchlist watchlist) {
        return repo.save(watchlist);
    }

    public Watchlist getWatchlistById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void addFilmToWatchlist(FilmEntry.Film film, Watchlist watchlist) {
        System.out.println("Adding film to watchlist: " + film);
        watchlist.getFilms().add(film);
        repo.save(watchlist);
    }

    public void removeFilmFromWatchlist(FilmEntry.Film film, Watchlist watchlist) {
        System.out.println("Removing film from watchlist: " + film);
        watchlist.getFilms().remove(film);
        repo.save(watchlist);
    }

    public Watchlist createWatchlist() {
        Watchlist watchlist = new Watchlist();
        System.out.println("Creating new watchlist: " + watchlist);
        return repo.save(watchlist);
    }
}
