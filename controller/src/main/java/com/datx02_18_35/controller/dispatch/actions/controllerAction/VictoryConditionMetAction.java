package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.game.VictoryInformation;

/**
 * Created by robin on 2018-03-13.
 */

public class VictoryConditionMetAction extends Action {
    public final String secretMessage = "YOU'RE THA BOMB!";

    public final VictoryInformation victoryInformation;

    public VictoryConditionMetAction(VictoryInformation victoryInformation) {
        this.victoryInformation = victoryInformation;
    }
}
