package com.datx02_18_35.model.game;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;

/**
 * Created by robin on 2018-04-10.
 */

public class VictoryInformation {
    public final int previousScore;
    public final int newScore;
    public final boolean hasNextLevel;
    public final Expression goal;
    public final LevelCategory unlockedCategory;

    public VictoryInformation(int previousScore,
                              int newScore,
                              boolean hasNextLevel,
                              Expression goal,
                              LevelCategory unlockedCategory) {

        this.previousScore = previousScore;
        this.newScore = newScore;
        this.hasNextLevel = hasNextLevel;
        this.goal = goal;
        this.unlockedCategory = unlockedCategory;
    }
}
