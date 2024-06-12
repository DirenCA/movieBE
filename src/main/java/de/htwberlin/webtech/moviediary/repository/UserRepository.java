package de.htwberlin.webtech.moviediary.repository;


import de.htwberlin.webtech.moviediary.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}


