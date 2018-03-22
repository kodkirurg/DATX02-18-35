package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by Jonatan on 2018-03-22.
 */

public class RefreshScopeLevelAction extends Action {
    public final int scopeLevel;
    public RefreshScopeLevelAction(int scopeLevel){
        this.scopeLevel=scopeLevel;
    }
}
