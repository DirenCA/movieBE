package de.htwberlin.webtech.moviediary.controller;

import de.htwberlin.webtech.moviediary.exception.UserNotFoundException;
import de.htwberlin.webtech.moviediary.model.FilmUser;
import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.Watchlist;
import de.htwberlin.webtech.moviediary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3003")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<FilmUser> registerUser(@RequestBody FilmUser filmUser) {
        FilmUser user = userService.registerUser(filmUser.getUserName(), filmUser.getPassword());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody FilmUser filmUser) {
        userService.deleteUser(filmUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<FilmUser> loginUser(@RequestBody FilmUser filmUser) {
        try {
            FilmUser user = userService.loginUser(filmUser.getUserName(), filmUser.getPassword());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/watchlist")
    public ResponseEntity<Watchlist> addMovieToWatchlist(@RequestBody FilmEntry.Film film, @RequestHeader("Authorization") String token) {
        Watchlist watchlist = userService.addMovieToWatchlist(token, film);
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/watchlist")
    public ResponseEntity<Watchlist> getWatchlist(@RequestHeader("Authorization") String token) {
        Watchlist watchlist = userService.getWatchlist(token);
        return ResponseEntity.ok(watchlist);
    }
}
