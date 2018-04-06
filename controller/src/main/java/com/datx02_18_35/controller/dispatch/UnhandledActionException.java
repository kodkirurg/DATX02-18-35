package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.GameException;

/**
 * Created by robin on 2018-03-06.
 */

public class UnhandledActionException extends GameException {
    public final Action action;
    public UnhandledActionException(Action action) {
        super(action.toString());
        this.action = action;
    }

    public UnhandledActionException(UnhandledActionException ex) {
        this(ex.action);
    }
}
