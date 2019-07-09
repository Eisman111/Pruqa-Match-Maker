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

    @Scheduled(fixedRate = 5000)
    private void combinePlayers() {

        try {

            service.combinePlayers();
        } catch (NoPlayerToMatchFoundException ne) {
            log.info("DEBUG --> No player to match");
        } catch (NoMatchFoundException ne) {
            log.warn("No match found for player {}", ne.getPlayer());
        } catch (OptimisticLockingFailureException olfe) {
            log.info("DEBUG --> locked record");
        } catch (RuntimeException re) {
            log.error("Error {}", re.getMessage());
//            throw new RuntimeException(re);
        }
    }
}
