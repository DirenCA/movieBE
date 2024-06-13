package de.htwberlin.webtech.moviediary.repository;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.FilmUser;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<FilmEntry.Film, Long> {
}
