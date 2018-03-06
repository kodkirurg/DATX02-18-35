package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-06.
 */

public class UnhandledActionException extends Exception {
    public final Action action;
    public UnhandledActionException(Action action) {
        super(action.toString());
        this.action = action;
    }

    public UnhandledActionException(UnhandledActionException ex) {
        this(ex.action);
    }
}
