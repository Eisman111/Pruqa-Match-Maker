package com.pruqa.matchmakercombiner.service;

import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.Player;
import org.springframework.transaction.annotation.Transactional;

public abstract class CombinerService implements ICombinerService {

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
            updateMatchMakingTentative(player);
        }
    }

    protected abstract Player findPlayerToCombine();

    protected abstract Player findPlayerMatch(Player player);

    protected abstract void updatePlayerStatus(Player matchedPlayer, MatchMakingStatus matched);

    protected abstract void notifyClient(Player player, Player matchedPlayer);

    abstract void updateMatchMakingTentative(Player player);
}
