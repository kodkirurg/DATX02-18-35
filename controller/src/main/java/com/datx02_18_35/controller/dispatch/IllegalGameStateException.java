package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-13.
 */

public class IllegalGameStateException extends Exception {
    public final Action action;

    public IllegalGameStateException(Action action) {
        super(action.toString());
        this.action = action;
    }

    public IllegalGameStateException(IllegalGameStateException ex) {
        this(ex.action);
    }
}
