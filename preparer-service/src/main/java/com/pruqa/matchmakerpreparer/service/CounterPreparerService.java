package com.pruqa.matchmakerpreparer.service;

import com.pruqa.matchmakerpreparer.exception.InvalidMatchMakingAlgorithmException;
import com.pruqa.matchmakerpreparer.exception.UnavailablePreparationException;
import com.pruqa.matchmakerpreparer.generated.SettingsControllerApi;
import com.pruqa.matchmakerpreparer.generated.invoker.ApiException;
import com.pruqa.matchmakerpreparer.generated.model.GameAttribute;
import com.pruqa.matchmakerpreparer.generated.model.GameSetting;
import com.pruqa.matchmakerpreparer.generated.model.SettingsRequest;
import com.pruqa.matchmakerpreparer.model.MatchMakingStatus;
import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import com.pruqa.matchmakerpreparer.model.Player;
import com.pruqa.matchmakerpreparer.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CounterPreparerService extends PreparerService {

    private SettingsControllerApi settingsApi;
    private PlayerRepository repository;

    @Autowired
    public CounterPreparerService(final SettingsControllerApi settingsApi, final PlayerRepository repository) {
        this.settingsApi = settingsApi;
        this.repository = repository;
    }

    /**
     * Get the input player message and convert it into the Player object by cloning it's properties
     * @param messagePlayer rabbit Player Message
     * @return player
     */
    @Override
    Player convertMessage(MessagePlayer messagePlayer) {
        Player player = new Player();
        BeanUtils.copyProperties(messagePlayer,player);
        player.setStatus(MatchMakingStatus.INIT);
        player.setLastUpdate(new Date());
        return player;
    }

    /**
     * Fetch the game settings from the SettingsAPI searching by game name
     * In case of failure to contact settings API an UnavailablePreparationException will be thrown
     * @param player Player
     * @return game settings
     */
    @Override
    GameSetting fetchGameSettings(Player player) {

        GameSetting gameSetting;

        try {
            SettingsRequest request = new SettingsRequest();
            request.setGameName(player.getGameName());
            gameSetting = settingsApi.getGameRulesUsingPOST("requestId","sessionId",request);
        } catch (ApiException ae) {
            log.error("While trying to fetch the game rules for the game {}, " +
                    "we received the error code: {}, and message {}", player.getGameName(), ae.getCode() ,ae.getResponseBody());
            throw new UnavailablePreparationException(ae.getMessage());
        }

        return gameSetting;
    }

    /**
     * Add the game settings to the player
     * @param player input player
     * @param gameSettings settings for specific game
     */
    @Override
    void addPlayerSettings(final Player player, final GameSetting gameSettings) {
        player.setGameSetting(gameSettings);
    }

    /**
     * Simple calculator for skill points matching player attributes with game rules multiplier
     * In case of points equals or less then 0, meaning no match is found, a InvalidMatchMakingAlgorithmException is thrown
     * @param player input player
     * @return
     */
    @Override
    Double calculateSkillPoints(Player player) {
        double points = 0.0;

        for(GameAttribute attribute : player.getGameSetting().getAttributes()) {
            points += player.getCharacteristics().get(attribute.getAttributeName()) * attribute.getAttributeMultiplier();
        }

        if (points <= 0) {
            throw new InvalidMatchMakingAlgorithmException("No properties are matched");
        }

        return points;
    }

    /**
     * Add the input skill points to the player
     * @param player Player
     * @param points match making points
     */
    @Override
    void addSPlayerkillPoints(Player player, Double points) {
        player.setPoints(points);
    }

    /**
     * Save the player on the repository for further elaboration
     * In case of any problem throw a UnavailablePreparationException
     * @param player Player
     * @return player added to the repository
     */
    @Override
    Player savePlayer(Player player) {
        Player savedPlayer;

        try {
            savedPlayer = repository.save(player);
        } catch (RuntimeException re) {
            log.error("While trying to save the player, {}, to the database, error {}", player.toString(), re.getMessage());
            throw new UnavailablePreparationException(re.getMessage());
        }

        return savedPlayer;
    }
}
