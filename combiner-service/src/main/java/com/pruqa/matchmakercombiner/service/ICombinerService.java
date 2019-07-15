package com.pruqa.matchmakercombiner.service;

import org.springframework.transaction.annotation.Transactional;

public interface ICombinerService {

    /**
     * Combine two players for match making.
     * Keep it transactional
     */
    @Transactional
    void combinePlayers();
}
