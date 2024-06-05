package de.htwberlin.webtech.moviediary.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.json.JSONObject;

@RestController
@CrossOrigin(origins = "https://moviefrontend-lxaz.onrender.com") //http://localhost:3002" //Hier geben wir an, von welcher URL aus Anfragen angenommen werden sollen https://moviefrontend-lxaz.onrender.com
public class MyController {

    private FilmEntry filmEntry = new FilmEntry();

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
