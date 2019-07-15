package com.pruqa.matchmakercombiner.scheduler;

import com.pruqa.matchmakercombiner.service.IWakerCombinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WakerPlayersScheduler {

    private IWakerCombinerService service;

    @Autowired
    public WakerPlayersScheduler(IWakerCombinerService service) {
        this.service = service;
    }

    /**
     * On a fixed rate try to wake up each player that is sleeping
     */
    @Scheduled(fixedRate = 1000)
    private void wakeUp() {
        service.wakeUpPlayers();
    }
}
