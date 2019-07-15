package com.pruqa.matchmakerpreparer.service;

import com.pruqa.matchmakerpreparer.generated.model.GameSetting;
import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import com.pruqa.matchmakerpreparer.model.Player;

public abstract class PreparerService implements IPreparerService {

    /**
     * Prepare the player for matchmaking
     * @param messagePlayer rabbit player message
     * @return player added to the db
     */
    @Override
    public Player prepareFlow(final MessagePlayer messagePlayer) {

        Player player = convertMessage(messagePlayer);

        final var gameSettings = fetchGameSettings(player);

        addPlayerSettings(player, gameSettings);

        final var points = calculateSkillPoints(player);

        addSPlayerkillPoints(player, points);

        return savePlayer(player);
    }

    /**
     * Convert input message from queue to the Player object
     * @param messagePlayer rabbit Player Message
     * @return Player
     */
    abstract Player convertMessage(final MessagePlayer messagePlayer);

    /**
     * Calculate points for matchmaking
     * @param player Player
     * @return player
     */
    abstract GameSetting fetchGameSettings(final Player player);

    /**
     * Add game settings to player
     * @param player input player
     * @param gameSettings settings for specific game
     */
    abstract void addPlayerSettings(Player player, GameSetting gameSettings);

    /**
     * Calculate the skill points based on player properties
     * @param player input player
     */
    abstract Double calculateSkillPoints(final Player player);

    /**
     * Enrich player with the recovered points
     * @param player Player
     * @param points match making points
     */
    abstract void addSPlayerkillPoints(final Player player, final Double points);

    /**
     * Save player to database
     * @param player Player
     */
    abstract Player savePlayer(final Player player);
}
