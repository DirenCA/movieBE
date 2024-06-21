package de.htwberlin.webtech.moviediary.repository;


import de.htwberlin.webtech.moviediary.model.FilmUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<FilmUser, Long> {
    FilmUser findByUserNameAndPassword(String userName, String password);

    FilmUser findByToken(String token);

    FilmUser findByUserName(String userName);
}


