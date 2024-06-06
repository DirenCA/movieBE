package de.htwberlin.webtech.moviediary.service;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    @Autowired
    FilmRepository repo;

    public FilmEntry.Film saveFilm(FilmEntry.Film film) {
        return repo.save(film);
    }

    public FilmEntry.Film getFilmById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
