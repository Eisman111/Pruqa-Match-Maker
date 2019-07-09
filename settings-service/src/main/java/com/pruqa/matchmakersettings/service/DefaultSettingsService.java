package com.pruqa.matchmakersettings.service;

import com.pruqa.matchmakersettings.exceptions.InvalidCompanyException;
import com.pruqa.matchmakersettings.exceptions.InvalidGameSettingsException;
import com.pruqa.matchmakersettings.model.*;
import com.pruqa.matchmakersettings.repository.GameRepository;
import com.pruqa.matchmakersettings.repository.AttributeRepository;
import com.pruqa.matchmakersettings.repository.SettingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class DefaultSettingsService extends SettingsService {

    // ==== fields ====
    private GameRepository gameRepository;
    private AttributeRepository attributeRepository;
    private SettingsRepository settingsRepository;

    // ==== constructor ====
    @Autowired
    public DefaultSettingsService (final GameRepository gameRepository, final AttributeRepository attributeRepository, final SettingsRepository settingsRepository) {
        this.gameRepository = gameRepository;
        this.attributeRepository = attributeRepository;
        this.settingsRepository = settingsRepository;
    }

    // ==== internal methods ====
    /**
     * Verify if the company and game are registered in the database
     * If there is no recovered a game for company ID and game ID throw an exception
     *
     * @param companyRequest id of the company and the id of the game
     * @return game
     */
    @Override
    Game verifyCompanyAndGameRegistration(CompanyRequest companyRequest) {

        log.debug("The input companyRequest object is {}", companyRequest.toString());

        Game game = gameRepository.findByCompanyNameAndGameName(companyRequest.getCompanyName(),companyRequest.getGameName());

        if (game == null) {
            log.error("Unable to find game for the input company name: {}, and game Id: {}",companyRequest.getCompanyName(),companyRequest.getGameName());
            throw new InvalidCompanyException();
        }

        log.info("Successfully recovered a game from the db with the companyId: {}, gameId: {}", companyRequest.getCompanyName(),companyRequest.getGameName());
        log.debug("The recovered game object is: {}", game.toString());

        return game;
    }

    /**
     * Verify if the game settings are set correctly
     * If there is no game response endpoint then responde INVALID_GAME_SETTINGS
     * If there is no game rules then respond INVALID_GAME_RULES
     *
     * @param game object
     */
    @Override
    void verifyGameSettingsValidity(Game game) {

        //TODO really need this? Rest validation isn't enough?

        if (game.getResponseEndpoint() == null) {
            log.error("The game settings recovered in the database are not valid to be used. {}", game.toString());
            throw new InvalidGameSettingsException();
        }

        log.info("The validation of settings and rules was successful for game id {}", game.getGameId());
    }

    /**
     * Recover the game by searching for it's ID
     * If you don't find the game respond INVALID_GAME_ID
     * If the attributes of the game are not present then responde INVALID_GAME_RULES
     * Else responde OK with the attributes in the payload
     *
     * @param settingsRequest with id of the game
     * @return gameResponse with game in payload
     */
    @Override
    GameSetting fetchGameSettingsByGameName(final SettingsRequest settingsRequest) {

        log.debug("The settingsRequest object is {}", settingsRequest.getGameName());

        Game game = gameRepository.findByGameName(settingsRequest.getGameName());

        if (game == null) {
            log.error("Unable to find game for the input game Id: {}", settingsRequest.getGameName());
            throw new InvalidCompanyException();
        }

        verifyGameSettingsValidity(game);

        log.info("Successfully recovered game rules for the game id: {}", game.getGameId());
        log.debug("The recovered game is: {}", game.toString());

        return game.getGameSetting();
    }

    /**
     * Method useful to add a game to the database
     * It use the reflection to save the properties to the game object
     * If it fails with the reflection operation then respond with a generic error
     *
     * @param game input rest object
     * @return addGameResponse with game as payload
     */
    @Override
    GameResponse addGameToDB(final Principal principal, final Game game) {

        log.debug("The gameRequest object is {}", game.toString());

        game.getGameSetting().getAttributes().forEach(a -> attributeRepository.save(a));

        game.setCompanyName(principal.getName());

        game.setGameStatus("ACTIVE");

        Game resultGame = gameRepository.save(game);

        log.info("Successfully added a game to the database with the game id {}", resultGame.getGameId());
        log.debug("The registered game is {}", resultGame.toString());

        return new GameResponse(principal.getName(),resultGame.getGameName());
    }
}
