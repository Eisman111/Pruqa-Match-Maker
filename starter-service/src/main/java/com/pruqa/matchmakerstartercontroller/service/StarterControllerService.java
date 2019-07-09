package com.pruqa.matchmakerstartercontroller.service;

import com.pruqa.matchmakerstartercontroller.model.Player;

import java.security.Principal;

public abstract class StarterControllerService implements StarterControllerInterfaceService {

    // ==== methods ====
    /**
     * When you receive a player that need to be added to the match making service use the following logic:
     * 1. Verify the game settings
     * 2. Add the message to the rabbit queue
     *
     * @param principal company logged in
     * @param player input
     * @return string
     */
    @Override
    public String addPlayerToMatchMakerFlow(final Principal principal, final Player player) {

        verifyGameAccessibilityAndRules(principal,player);

        return addPlayerToQueue(player);
    }

    /**
     * Verify if the game is registered and if the rules are defined
     *
     * @param principal object defined in the request
     * @param player object defined in the request
     * @return addPlayerResponse
     */
    abstract void verifyGameAccessibilityAndRules(final Principal principal, final Player player);

    /**
     * Add the player to the queue for match making service
     *
     * @param player object defined in the request
     * @return addPlayerResponse
     */
    abstract String addPlayerToQueue(final Player player);
}
