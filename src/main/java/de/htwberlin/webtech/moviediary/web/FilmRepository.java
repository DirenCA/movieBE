package de.htwberlin.webtech.moviediary.web;

import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<FilmEntry.Film, Long> { }
