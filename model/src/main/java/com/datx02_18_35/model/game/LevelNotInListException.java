package com.datx02_18_35.model.game;

import com.datx02_18_35.model.GameException;

/**
 * Created by robin on 2018-04-05.
 */

public class LevelNotInListException extends GameException {
    public LevelNotInListException(String s){
        super(s);
    }
}