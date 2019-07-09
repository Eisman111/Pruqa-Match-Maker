package com.pruqa.matchmakercombiner.repository;

import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface PlayerRepository extends MongoRepository<Player, String> {

    Player findTopByStatusIsNotAndGameNameOrderByPointsDesc(MatchMakingStatus status, String gameName);

    Player findTopByStatusIsNotAndGameNameOrderByPointsAsc(MatchMakingStatus status, String gameName);

    Player findTopByStatusOrderByLastUpdateDesc(MatchMakingStatus status);

    Set<Player> findByStatusAndGameNameOrderByLastUpdateDesc(MatchMakingStatus status, String gameName);

    Set<Player> findByStatus(MatchMakingStatus status);

    Set<Player> findByIdNotAndGameNameAndPointsIsBetweenAndStatusNot(String id, String gameName, Double min, Double max, MatchMakingStatus done);
}
