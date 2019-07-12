package com.pruqa.matchmakercombiner.service;

import com.pruqa.matchmakercombiner.messanger.CombinerProducer;
import com.pruqa.matchmakercombiner.messanger.DefaultCombinerProducer;
import com.pruqa.matchmakercombiner.model.MatchMakingStatus;
import com.pruqa.matchmakercombiner.model.Player;
import com.pruqa.matchmakercombiner.repository.PlayerRepository;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
import com.pruqa.matchmakerlibrary.model.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Slf4j
@Service
public class DefaultWakerCombinerService extends WakerCombinerService {

    private PlayerRepository playerRepository;
    private CombinerProducer combinerProducer;

    @Autowired
    public DefaultWakerCombinerService(final PlayerRepository playerRepository, final DefaultCombinerProducer combinerProducer) {
        this.playerRepository = playerRepository;
        this.combinerProducer = combinerProducer;
    }

    @Override
    Set<Player> recoverSleepingPLayers() {
        return playerRepository.findByStatus(MatchMakingStatus.SLEEP);
    }

    @Override
    boolean isWakeUpTime(Player player) {

        long lastUpdateAfterSleep = player.getLastUpdate().getTime() + player.getGameSetting().getIncrementalWaitTime();

        Date date = new Date();

        return date.getTime() >= lastUpdateAfterSleep && player.getMatchOperation().getTentative() <= player.getGameSetting().getIncrementalRounds();
    }

    @Override
    void updatePlayerStatus(Player player, MatchMakingStatus status) {
        player.setStatus(status);
        player.setLastUpdate(new Date());
        playerRepository.save(player);
    }

    @Override
    boolean isFailedMatch(final Player player) {
        return player.getMatchOperation().getTentative() > player.getGameSetting().getIncrementalRounds();
    }

    @Override
    void notifyMatchFailure(Player player) {
        log.error("Failed to match player {}", player.toString());
        combinerProducer
                .addToResultQueue(MatchResultMessage
                .builder()
                .responseCode(ResponseCode.NO_MATCHING_PLAYER)
                .responseMessage("Additional message")
                .inputPlayer(player.getPlayerId())
                .gameName(player.getGameName())
                .build());
    }
}
