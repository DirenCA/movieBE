package de.htwberlin.webtech.moviediary.controller;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.Watchlist;
import de.htwberlin.webtech.moviediary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchlist")
@CrossOrigin(origins = "http://localhost:3003")
public class WatchListController {

    private final UserService userService;

    @Autowired
    public WatchListController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Watchlist> addMovieToWatchlist(@RequestHeader("Authorization") String token, @RequestBody FilmEntry.Film film) {
        try {
            Watchlist watchlist = userService.addMovieToWatchlist(token, film);
            return ResponseEntity.ok(watchlist);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
