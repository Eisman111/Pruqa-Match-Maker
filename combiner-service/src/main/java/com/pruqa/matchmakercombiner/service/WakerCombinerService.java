package com.pruqa.matchmakercombiner.service;

import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.Player;

import java.util.Set;

public abstract class WakerCombinerService implements IWakerCombinerService{

    /**
     * Fetch each sleeping player, if is wakeup time and tentative are less then the limits then wake up time
     * if else the tentative are too much it's time to kill the player and notify the client
     */
    @Override
    public void wakeUpPlayers() {

        Set<Player> sleepingPlayers = recoverSleepingPLayers();

        sleepingPlayers
                .forEach(player -> {
                    if (isWakeUpTime(player)) {
                        updatePlayerStatus(player, MatchMakingStatus.INIT);
                    } else if (isFailedMatch(player)) {
                        updatePlayerStatus(player, MatchMakingStatus.MATCHED); //TODO REFACTOR, USING THIS STATUS FOR QUERYING ISSUE
                        notifyMatchFailure(player);
                    }
                });
    }

    /**
     * Has the player been tried to wake up too many times?
     * @param player db player
     * @return boolean
     */
    abstract boolean isFailedMatch(Player player);

    /**
     * Notify the client of a no matching player found for the input player
     * @param player db player
     */
    abstract void notifyMatchFailure(Player player);

    /**
     * Fetch all the sleeping players
     * @return set of players
     */
    abstract Set<Player> recoverSleepingPLayers();

    /**
     * Is the player sleeping for enough time and the tentative are less then the limit?
     * @param player fetched player
     * @return boolean
     */
    abstract boolean isWakeUpTime(Player player);

    /**
     * Update the player status and save it into the repository
     * @param player
     * @param status
     */
    abstract void updatePlayerStatus(Player player, MatchMakingStatus status);
}
