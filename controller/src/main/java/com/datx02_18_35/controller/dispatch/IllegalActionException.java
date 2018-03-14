package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-13.
 */

public class IllegalActionException extends Exception {
    public final Action action;

    public IllegalActionException(Action action) {
        super(action.toString());
        this.action = action;
    }

    public IllegalActionException(IllegalActionException ex) {
        this(ex.action);
    }
}
