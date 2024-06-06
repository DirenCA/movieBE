package de.htwberlin.webtech.moviediary.controller;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.Watchlist;
import de.htwberlin.webtech.moviediary.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3003")
@RequestMapping("/watchlist")
public class WatchListController {

    private final WatchlistService watchlistService;

    @Autowired
    public WatchListController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping
    public ResponseEntity<Void> addToWatchlist(@RequestBody FilmEntry.Film film) {
        Watchlist watchlist = watchlistService.createWatchlist();
        watchlistService.addFilmToWatchlist(film, watchlist);
        return ResponseEntity.ok().build();
    }
}
