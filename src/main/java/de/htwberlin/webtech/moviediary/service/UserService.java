package de.htwberlin.webtech.moviediary.service;

import de.htwberlin.webtech.moviediary.exception.UserNotFoundException;
import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.FilmUser;
import de.htwberlin.webtech.moviediary.model.Watchlist;
import de.htwberlin.webtech.moviediary.repository.FilmRepository;
import de.htwberlin.webtech.moviediary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private FilmRepository filmRepository;

    public FilmUser registerUser(String userName, String password) {
        Watchlist watchlist = watchlistService.createWatchlist();
        FilmUser filmUser = new FilmUser(userName, password, watchlist);
        watchlist.setFilmUser(filmUser); // Setzen des FilmUser in der Watchlist
        return repo.save(filmUser);
    }

    public void deleteUser(FilmUser filmUser) {
        if (filmUser != null && repo.existsById(filmUser.getId())) {
            repo.delete(filmUser);
        } else {
            throw new UserNotFoundException("User not found: " + (filmUser != null ? filmUser.getUserName() : "unknown"));
        }
    }

    public FilmUser loginUser(String userName, String password) {
        FilmUser filmUser = repo.findByUserName(userName);
        if (filmUser != null && filmUser.getPassword().equals(password)) {
            String token = UUID.randomUUID().toString();
            filmUser.setToken(token);
            repo.save(filmUser);
            return filmUser;
        } else {
            throw new UserNotFoundException("Invalid username or password for user: " + userName);
        }
    }

    public Watchlist addMovieToWatchlist(String token, FilmEntry.Film film) {
        FilmUser filmUser = repo.findByToken(token);
        if (filmUser != null) {
            Watchlist watchlist = filmUser.getWatchlist();
            if (watchlist == null) {
                watchlist = watchlistService.createWatchlist();
                filmUser.setWatchlist(watchlist);
                repo.save(filmUser);
            }

            // Prüfen, ob der Film bereits in der Datenbank vorhanden ist
            Optional<FilmEntry.Film> existingFilm = filmRepository.findById(film.getId());
            if (!existingFilm.isPresent()) {
                film = filmRepository.save(film);
            } else {
                film = existingFilm.get();
            }

            watchlist.getFilms().add(film);
            watchlistService.saveWatchlist(watchlist);
            return watchlist;
        } else {
            throw new UserNotFoundException("Invalid token: " + token);
        }
    }

    public Watchlist getWatchlist(String token) {
        FilmUser filmUser = repo.findByToken(token);
        if (filmUser != null) {
            return filmUser.getWatchlist();
        } else {
            throw new UserNotFoundException("Invalid token: " + token);
        }
    }
}
