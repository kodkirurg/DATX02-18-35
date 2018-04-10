package com.datx02_18_35.model.userdata;

import java.io.Serializable;

/**
 * Created by robin on 2018-03-15.
 */

public class LevelProgression implements Serializable {
    public final boolean completed;
    public final int stepsApplied;

    public LevelProgression() {
        this.completed = false;
        this.stepsApplied = -1;
    }

    public LevelProgression(int stepsApplied) {
        if (stepsApplied < 0) {
            throw new IllegalArgumentException("Steps applied must be negative");
        }

        this.completed = true;
        this.stepsApplied = stepsApplied;
    }
}
