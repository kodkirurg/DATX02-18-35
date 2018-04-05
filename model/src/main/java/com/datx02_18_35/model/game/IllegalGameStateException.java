package com.datx02_18_35.model.game;

import com.datx02_18_35.model.GameException;

/**
 * Created by robin on 2018-03-14.
 */

public class IllegalGameStateException extends GameException {
    public IllegalGameStateException(String message) {
        super(message);
    }
}
