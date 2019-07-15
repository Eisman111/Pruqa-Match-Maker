package com.pruqa.matchmakerpreparer.service;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import com.pruqa.matchmakerpreparer.model.Player;

public interface IPreparerService {

    /**
     * Prepare the player for matchmaking
     * @param messagePlayer rabbit player message
     * @return player added to the db
     */
    Player prepareFlow(final MessagePlayer messagePlayer);
}
