package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-13.
 */

public class VictoryConditionMetAction extends Action {
    public final String secretMessage = "YOU'RE THA BOMB!";

    public final int currentScore;
    public final boolean hasNextLevel;
    public final Expression goal;

    /**
     * Negative value of previousScore denotes the level has not been completed before
     */
    public final int previousScore;

    public VictoryConditionMetAction(Expression goal,int currentScore, int previousScore,boolean hasNextLevel) {
        this.currentScore = currentScore;
        this.previousScore = previousScore;
        this.hasNextLevel = hasNextLevel;
        this.goal=goal;
    }
}
