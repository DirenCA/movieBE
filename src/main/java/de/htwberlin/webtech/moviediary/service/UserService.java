package de.htwberlin.webtech.moviediary.service;

import de.htwberlin.webtech.moviediary.model.FilmUser;
import de.htwberlin.webtech.moviediary.model.Watchlist;
import de.htwberlin.webtech.moviediary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    WatchlistService watchlistService;

    public FilmUser registerUser(String userName, String password) {
        Watchlist watchlist = watchlistService.createWatchlist();

        FilmUser filmUser = new FilmUser(userName, password, watchlist);

        return repo.save(filmUser);
    }

    public void deleteUser(FilmUser filmUser) {
        repo.delete(filmUser);
    }

    public FilmUser loginUser (String userName, String password) {
        FilmUser filmUser = repo.findByUserNameAndPassword(userName, password);
        if (filmUser != null) {
            return filmUser;
        } else {
            throw new IllegalArgumentException("User not found with username: " + userName);
        }
    }

}
