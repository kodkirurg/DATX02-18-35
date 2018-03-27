package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.game.Level;

/**
 * Created by raxxor on 2018-03-27.
 */

public class RefreshCurrentLevelAction extends Action {
    public Level level;
    public RefreshCurrentLevelAction(Level level){
        this.level=level;
    }
}
