package com.pruqa.matchmakerpreparer.repository;

import com.pruqa.matchmakerpreparer.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findByPointsBetween(int min, int max);
}
