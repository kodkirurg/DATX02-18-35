package com.datx02_18_35.model.game;

import java.io.Serializable;

/**
 * Created by robin on 2018-03-15.
 */

public class LevelProgression implements Serializable {
    public boolean completed = false;
    public int stepsApplied = 0;

    @Override
    public LevelProgression clone() {
        LevelProgression lp = new LevelProgression();
        lp.completed = this.completed;
        lp.stepsApplied = this.stepsApplied;
        return lp;
    }
}
