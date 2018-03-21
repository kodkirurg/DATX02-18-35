package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-13.
 */

public class VictoryConditionMetAction extends Action {
    public final String secretMessage = "YOU'RE THA BOMB!";

    public final int currentScore;

    /**
     * Negative value of previousScore denotes the level has not been completed before
     */
    public final int previousScore;

    public VictoryConditionMetAction(int currentScore, int previousScore) {
        this.currentScore = currentScore;
        this.previousScore = previousScore;
    }
}
