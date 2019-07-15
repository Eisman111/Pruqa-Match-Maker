package com.pruqa.matchmakercombiner.scheduler;

import com.pruqa.matchmakercombiner.exception.NoMatchFoundException;
import com.pruqa.matchmakercombiner.exception.NoPlayerToMatchFoundException;
import com.pruqa.matchmakercombiner.service.CounterCombinerService;
import com.pruqa.matchmakercombiner.service.ICombinerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CombinePlayersScheduler {

    private ICombinerService service;

    public CombinePlayersScheduler(CounterCombinerService service) {
        this.service = service;
    }

    /**
     * On a fixed rate try to start the flow for combining two players and manage each possible exception
     */
    @Scheduled(fixedRate = 5000)
    private void combinePlayers() {
        try {
            service.combinePlayers();
        } catch (NoPlayerToMatchFoundException ne) {
            log.info("No player to match");
        } catch (NoMatchFoundException ne) {
            log.info("No match found for player {}", ne.getPlayer());
        } catch (OptimisticLockingFailureException olfe) {
            log.trace("Locked record {}", olfe.getMessage());
        } catch (RuntimeException re) {
            log.error("Error {}", re.getMessage());
            throw new RuntimeException(re);
        }
    }
}
