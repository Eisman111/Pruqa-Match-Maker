package com.pruqa.matchmakerpreparer.service;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import com.pruqa.matchmakerpreparer.model.Player;

public interface IPreparerService {

    Player prepareFlow(final MessagePlayer messagePlayer);
}
