package com.pruqa.matchmakercombiner.exception;

import com.pruqa.matchmakercombiner.model.Player;
import lombok.Getter;

@Getter
public class NoMatchFoundException extends RuntimeException {

    private Player player;

    public NoMatchFoundException(Player player) {
        this.player = player;
    }
}
