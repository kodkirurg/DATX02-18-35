package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-08.
 */

public class RequestGameboardAction extends Action {
    public RequestGameboardAction(ActionConsumer callback) {
        super(callback);
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
    }
}
