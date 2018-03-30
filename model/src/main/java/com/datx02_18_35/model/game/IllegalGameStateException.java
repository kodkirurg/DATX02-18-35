package com.datx02_18_35.model.game;

/**
 * Created by robin on 2018-03-14.
 */

public class IllegalGameStateException extends Exception {
    public IllegalGameStateException(String message) {
        super(message);
    }
}
