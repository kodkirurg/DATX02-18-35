package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.model.game.Level;

/**
 * Created by robin on 2018-03-13.
 */

public class RequestStartNewSessionAction extends Action {
    public final Level level;

    public RequestStartNewSessionAction(ActionConsumer callback, Level level) {
        super(callback);
        this.level = level;
    }
}
