package de.htwberlin.webtech.moviediary.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.json.JSONObject;

@RestController
public class MyController {

    private FilmEntry filmEntry = new FilmEntry();

    @GetMapping("/films")
    public List<String> searchFilms(@RequestParam String query) {
        try {
            return filmEntry.searchFilmsByQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of("Fehler bei der Suche nach Filmen: " + e.getMessage());
        }
    }
}
