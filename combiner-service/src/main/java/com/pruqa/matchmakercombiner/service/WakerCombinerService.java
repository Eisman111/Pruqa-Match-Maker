package com.pruqa.matchmakercombiner.service;

import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.Player;

import java.util.Set;

public abstract class WakerCombinerService implements IWakerCombinerService{

    @Override
    public void wakeUpPlayers() {

        Set<Player> sleepingPlayers = recoverSleepingPLayers();

        sleepingPlayers
                .forEach(player -> {
                    if (isWakeUpTime(player)) {
                        updatePlayerStatus(player, MatchMakingStatus.INIT);
                    } else if (isFailedMatch(player)) {
                        updatePlayerStatus(player, MatchMakingStatus.MATCHED); //TODO REFACTOR
                        notifyMatchFailure(player);
                    }
                });
    }

    abstract boolean isFailedMatch(Player player);

    abstract void notifyMatchFailure(Player player);

    abstract Set<Player> recoverSleepingPLayers();

    abstract boolean isWakeUpTime(Player player);

    abstract void updatePlayerStatus(Player player, MatchMakingStatus status);
}
