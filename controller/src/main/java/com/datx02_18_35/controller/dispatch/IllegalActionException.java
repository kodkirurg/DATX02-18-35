package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.GameException;

/**
 * Created by robin on 2018-03-13.
 */

public class IllegalActionException extends GameException {
    public final Action action;

    public IllegalActionException(Action action) {
        super(action.toString());
        this.action = action;
    }

    public IllegalActionException(Action action, String message) {
        super(action.toString() + ": " + message);
        this.action = action;
    }

    public IllegalActionException(IllegalActionException ex) {
        this(ex.action);
    }
}
