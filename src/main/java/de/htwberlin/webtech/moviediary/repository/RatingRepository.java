package de.htwberlin.webtech.moviediary.repository;

import de.htwberlin.webtech.moviediary.model.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
}
