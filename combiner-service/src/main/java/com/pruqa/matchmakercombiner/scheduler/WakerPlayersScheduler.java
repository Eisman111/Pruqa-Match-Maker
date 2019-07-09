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

    @Scheduled(fixedRate = 1000)
    private void wakeUp() {
        service.wakeUpPlayers();
    }
}
