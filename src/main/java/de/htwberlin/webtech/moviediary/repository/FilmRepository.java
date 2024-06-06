package de.htwberlin.webtech.moviediary.repository;

import de.htwberlin.webtech.moviediary.model.FilmEntry;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<FilmEntry.Film, Long> { }
