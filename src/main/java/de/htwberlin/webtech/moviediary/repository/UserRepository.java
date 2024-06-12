package de.htwberlin.webtech.moviediary.repository;


import de.htwberlin.webtech.moviediary.model.FilmUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<FilmUser, Long> {
}


