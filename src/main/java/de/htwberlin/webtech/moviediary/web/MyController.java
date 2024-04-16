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
        FilmEntry entry = new FilmEntry("Test", "Description for Test", false);
        FilmEntry entry1 = new FilmEntry("Test1", "Description for Test1", false);
        FilmEntry entry2 = new FilmEntry("Test2", "Description for Test2", false);
        FilmEntry entry3 = new FilmEntry("Test3", "Description for Test3", false);
        FilmEntry entry4 = new FilmEntry("Test4", "Description for Test4", false);
        // Return a list of these objects
        return List.of(entry, entry1, entry2, entry3, entry4);
    }
}
