package de.htwberlin.webtech.moviediary.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

// Define the class as a RestController
@RestController
public class MyController {

    @GetMapping("/films")
    public List<FilmEntry> getFilms() {
        // Create several FilmEntry objects
        FilmEntry entry = new FilmEntry("Leon", "Action", true);
        FilmEntry entry1 = new FilmEntry("Der Pate", "Gangster", false);
        FilmEntry entry2 = new FilmEntry("Avengers", "Superhero", true);
        FilmEntry entry3 = new FilmEntry("Avatar", "SciFi", true);
        // Return a list of these objects
        return List.of(entry, entry1, entry2, entry3);
    }
}
