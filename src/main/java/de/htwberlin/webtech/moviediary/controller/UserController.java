package de.htwberlin.webtech.moviediary.controller;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.FilmUser;
import de.htwberlin.webtech.moviediary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        FilmUser user = userService.loginUser(filmUser.getUserName(), filmUser.getPassword());
        return ResponseEntity.ok(user);
    }

}

