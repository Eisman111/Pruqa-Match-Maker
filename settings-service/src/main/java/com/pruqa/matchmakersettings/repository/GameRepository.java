package com.pruqa.matchmakersettings.repository;

import com.pruqa.matchmakersettings.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    /**
     * Recover the game by company ID and gameId
     * @param companyName String
     * @param gameName int
     * @return game object
     */
    Game findByCompanyNameAndGameName(String companyName, String gameName);

    /**
     * Recover the game by ID
     * @param gameName int
     * @return game object
     */
    Game findByGameName(String gameName);

    @Query("SELECT gameName FROM Game " +
            "WHERE gameName = ?1 " +
            "AND companyName = ?2")
    Integer findGameIdByGameIdAndCompanyName(String gameName, String companyName);
}
