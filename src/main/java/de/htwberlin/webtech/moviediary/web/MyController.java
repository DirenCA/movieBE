package de.htwberlin.webtech.moviediary.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.json.JSONObject;

@RestController
@CrossOrigin(origins = "https://moviefrontend-lxaz.onrender.com") //http://localhost:3002" //Hier geben wir an, von welcher URL aus Anfragen angenommen werden sollen
public class MyController {

    private FilmEntry filmEntry = new FilmEntry();

    @GetMapping("/films")
    public List<String> searchFilms(@RequestParam String search) { //Hier wird der Suchbegriff als Parameter ("search") übergeben
        try {
            return filmEntry.searchFilmsByQuery(search);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of("Fehler bei der Suche nach Filmen: " + e.getMessage());
        }
    }
}
