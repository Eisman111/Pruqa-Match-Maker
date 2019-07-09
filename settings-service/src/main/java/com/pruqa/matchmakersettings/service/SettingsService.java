package com.pruqa.matchmakersettings.service;

import com.pruqa.matchmakersettings.model.*;

import java.security.Principal;


public abstract class SettingsService implements SettingsServiceInterface {

    // ==== public methods ====
    /**
     * Method usable to verify if the company and the game are valid
     *
     * @param companyRequest input registered in the rest controller
     */
    @Override
    public void verifyValidityCompanyAndGame(CompanyRequest companyRequest) {

        Game game = verifyCompanyAndGameRegistration(companyRequest);

        verifyGameSettingsValidity(game);
    }

    /**
     * Recover the list of game rules based on the game
     *
     * @param settingsRequest input providing the id of the game
     * @return gameResponse with the rules
     */
    @Override
    public GameSetting fetchGameSettings(final SettingsRequest settingsRequest) {
        return fetchGameSettingsByGameName(settingsRequest);
    }

    /**
     * Save a game object to the database
     * If the gameId is > 0 then verify ownership
     *
     * @param game rest input object
     * @return addGameResponse
     */
    @Override
    public GameResponse saveGame(final Principal principal, final Game game) {

        return addGameToDB(principal, game);
    }

    // ==== abstract methods ====
    /**
     * Verify if the company and the game are registered
     *
     * @param companyRequest id of the company and the id of the game
     * @return recovered game
     */
    abstract Game verifyCompanyAndGameRegistration(final CompanyRequest companyRequest);

    /**
     * Verify if game rules are set correctly
     *
     * @param game present on the database
     */
    abstract void verifyGameSettingsValidity(final Game game);

    /**
     * Recover the game rules from the database
     *
     * @param game id
     * @return gameResponse
     */
    abstract GameSetting fetchGameSettingsByGameName(final SettingsRequest game);

    /**
     * Save the game to the database
     *
     * @param game input rest object
     * @return addGameResponse
     */
    abstract GameResponse addGameToDB(final Principal princial, final Game game);
}
