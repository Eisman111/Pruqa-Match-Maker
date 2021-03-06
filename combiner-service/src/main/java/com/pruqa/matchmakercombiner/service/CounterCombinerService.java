package com.pruqa.matchmakercombiner.service;

import com.pruqa.matchmakercombiner.exception.NoPlayerToMatchFoundException;
import com.pruqa.matchmakercombiner.messanger.CombinerProducer;
import com.pruqa.matchmakercombiner.messanger.DefaultCombinerProducer;
import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.MatchOperation;
import com.pruqa.matchmakercombiner.model.Player;
import com.pruqa.matchmakercombiner.repository.PlayerRepository;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
import com.pruqa.matchmakerlibrary.model.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CounterCombinerService extends CombinerService {

    private PlayerRepository repository;
    private CombinerProducer combinerProducer;

    @Autowired
    public CounterCombinerService(final PlayerRepository repository, final DefaultCombinerProducer combinerProducer){
        this.repository = repository;
        this.combinerProducer = combinerProducer;
    }

    @Override
    protected Player findPlayerToCombine() {

        Player player = repository.findTopByStatusOrderByLastUpdateDesc(MatchMakingStatus.INIT);

        if (player == null) {
            throw new NoPlayerToMatchFoundException(null);
        }

        return player;
    }

    /**
     * Find a player that has a similar value of total points based on the range defined in the settings
     * For each new tentative increase the range and try to get the closest player
     * @param inputPlayer Player
     * @return Player
     */
    @Override
    protected Player findPlayerMatch(Player inputPlayer) {

        Player foundPlayer;

        if (inputPlayer.getMatchOperation() == null) {
            inputPlayer.setMatchOperation(MatchOperation.defaultInstanceOfMatchStatus());
        }

        final Map<String,Double> pointsBoundaries = getPointsBoundariesForGame(inputPlayer);

        Set<Player> players = repository.findByIdNotAndGameNameAndPointsIsBetweenAndStatusNot(
                inputPlayer.getId(),
                inputPlayer.getGameName(),
                pointsBoundaries.get("min"),
                pointsBoundaries.get("max"),
                MatchMakingStatus.MATCHED);

        foundPlayer = players
                .stream()
                .peek(player -> log.debug("Found player matching boundaries {}", player.toString()))
                .min(Comparator.comparingDouble(p -> Math.abs(p.getPoints() - inputPlayer.getPoints())))
                .orElse(null);

        return foundPlayer;
    }

    /**
     * Get the boundaries of the player total points based on settings absolute or relative value
     * Increase those boundaries for each tentative
     * @param inputPlayer Player
     * @return Map of boundaries
     */
    private Map<String, Double> getPointsBoundariesForGame(Player inputPlayer) {
        Map<String, Double> boundaries = new HashMap<>();

        Double absoluteRange = inputPlayer.getGameSetting().getAbsoluteRange();
        Double percentageRange = inputPlayer.getGameSetting().getPercentageRange();
        int tentative = inputPlayer.getMatchOperation().getTentative();
        Double points = inputPlayer.getPoints();

        Double minBoundarary;
        if (inputPlayer.getGameSetting().getAbsoluteRange() != 0) {
            minBoundarary = points - (absoluteRange * tentative);
        } else {
            minBoundarary = points - ((points * percentageRange) * tentative);
        }

        boundaries.put("min",minBoundarary);

        Double maxBoundarary;
        if (inputPlayer.getGameSetting().getAbsoluteRange() != 0) {
            maxBoundarary = points + (absoluteRange * tentative);
        } else {
            maxBoundarary = points + ((points * percentageRange) * tentative);
        }

        boundaries.put("max",maxBoundarary);

        return boundaries;
    }

    @Override
    protected void updatePlayerStatus(Player player, MatchMakingStatus matched) {
        player.setStatus(matched);
        player.setLastUpdate(new Date());
        repository.save(player);
    }

    @Override
    void increasePlayerTentatives(Player player) {
        player.getMatchOperation().setTentative(player.getMatchOperation().getTentative() + 1);
        repository.save(player);
    }

    @Override
    protected void notifyClient(Player player, Player matchedPlayer) {
        log.info("Matched two players {}, {}",player.toString(),matchedPlayer.toString());
        combinerProducer.addToResultQueue(buildMatchedPlayersMessage(player, matchedPlayer));
    }

    /**
     * Build the message for the queue with the resulting match between players
     * @param player Player
     * @param matchedPlayer Player
     * @return MatchResultMessage
     */
    private MatchResultMessage buildMatchedPlayersMessage(final Player player, final Player matchedPlayer) {
        return MatchResultMessage
                .builder()
                .responseCode(ResponseCode.MATCHED)
                .inputPlayer(player.getPlayerId())
                .matchedPlayer(matchedPlayer.getPlayerId())
                .gameName(player.getGameName())
                .build();
    }
}
