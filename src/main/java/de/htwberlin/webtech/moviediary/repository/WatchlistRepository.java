package de.htwberlin.webtech.moviediary.repository;

import de.htwberlin.webtech.moviediary.model.Watchlist;
import org.springframework.data.repository.CrudRepository;

public interface WatchlistRepository extends CrudRepository<Watchlist, Long>{ }
