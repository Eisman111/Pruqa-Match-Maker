package com.pruqa.matchmakercombiner.service;

import org.springframework.transaction.annotation.Transactional;

public interface ICombinerService {

    @Transactional
    void combinePlayers();
}
