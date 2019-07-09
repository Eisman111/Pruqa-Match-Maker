package com.pruqa.matchmakerstartercontroller.service;

import com.pruqa.matchmakerstartercontroller.model.Player;

import java.security.Principal;

public interface StarterControllerInterfaceService {

    /**
     * Add the player to the match maker service
     *
     * @param principal company logged in
     * @param player input
     * @return string
     */
    String addPlayerToMatchMakerFlow(final Principal principal, final Player player);
}
