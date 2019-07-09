package com.pruqa.matchmakercombiner.exception;

import com.pruqa.matchmakercombiner.model.Player;
import lombok.Getter;

@Getter
public class NoPlayerToMatchFoundException extends RuntimeException {

    private Player player;

    public NoPlayerToMatchFoundException(Player player) {
        this.player = player;
    }
}
