package com.pruqa.matchmakercombiner.service;

import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.Player;
import org.springframework.transaction.annotation.Transactional;

public abstract class CombinerService implements ICombinerService {

    /**
     * Try to match two players, if succeed then flag them as matched and notify the client
     * otherwise put the input player to sleep
     */
    @Override
    @Transactional
    public void combinePlayers() {
        Player player = findPlayerToCombine();

        updatePlayerStatus(player, MatchMakingStatus.EVALUATING);

        Player matchedPlayer = findPlayerMatch(player);

        if (matchedPlayer != null) {
            updatePlayerStatus(player, MatchMakingStatus.MATCHED);
            updatePlayerStatus(matchedPlayer, MatchMakingStatus.MATCHED);

            notifyClient(player,matchedPlayer);
        } else {
            updatePlayerStatus(player, MatchMakingStatus.SLEEP);
            increasePlayerTentatives(player);
        }
    }

    /**
     * Find the oldest player ready for a match
     * @return Player
     */
    protected abstract Player findPlayerToCombine();

    /**
     * Find a match for the input player
     * @param player Player
     * @return Player
     */
    protected abstract Player findPlayerMatch(Player player);

    /**
     * Update the player status and save it to the repository
     * @param matchedPlayer
     * @param matched
     */
    protected abstract void updatePlayerStatus(Player matchedPlayer, MatchMakingStatus matched);

    /**
     * Notify the client of the match result
     * @param player Player
     * @param matchedPlayer Player
     */
    protected abstract void notifyClient(Player player, Player matchedPlayer);

    /**
     * Increase player tentatives for match making
     * @param player Player
     */
    abstract void increasePlayerTentatives(Player player);
}
