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
        // Create a new watchlist for the user
        Watchlist watchlist = watchlistService.createWatchlist();

        // Create a new user
        FilmUser filmUser = new FilmUser(userName, password, watchlist);

        // Save the user in the database
        return repo.save(filmUser);
    }
}
