package com.pruqa.matchmakersettings.service;

import com.pruqa.matchmakersettings.model.*;

import java.security.Principal;

public interface SettingsServiceInterface {

    /**
     * Method usable to verify if the company and the game are valid
     *
     * @param companyRequest input registered in the rest controller

     */
    void verifyValidityCompanyAndGame(final CompanyRequest companyRequest);

    /**
     * Recover the list of rules for the specified game
     *
     * @param settingsRequest input providing the id of the game
     * @return gameResponse with the rules as payload
     */
    GameSetting fetchGameSettings(final SettingsRequest settingsRequest);

    /**
     * Add a game to the database
     * @param game rest input object
     * @return addGameResponse
     */
    GameResponse saveGame(final Principal principal, final Game game);
}
