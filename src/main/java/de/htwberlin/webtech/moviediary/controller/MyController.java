package de.htwberlin.webtech.moviediary.controller;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3003") //http://localhost:3003" //Hier geben wir an, von welcher URL aus Anfragen angenommen werden sollen https://moviefrontend-lxaz.onrender.com
public class MyController {

    private final FilmEntry filmEntry;

    @Autowired
    public MyController(FilmEntry filmEntry) {
        this.filmEntry = filmEntry;
    }

    @GetMapping("/films")
    public List<FilmEntry.Film> searchFilms(@RequestParam String search) {
        try {
            return filmEntry.searchFilmsByQuery(search);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @GetMapping("/popular")
    public List<FilmEntry.Film> getPopularFilms() {
        try {
            return filmEntry.getPopularFilms();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
