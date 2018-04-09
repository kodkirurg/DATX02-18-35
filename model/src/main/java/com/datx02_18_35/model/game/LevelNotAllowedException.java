package com.datx02_18_35.model.game;

import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.level.Level;

/**
 * Created by robin on 2018-04-09.
 */

public class LevelNotAllowedException extends GameException {
    public LevelNotAllowedException(Level level, String message) {
        super("Level: " + level.toString() + ". " +  message);
    }
}
