package com.datx02_18_35.controller.dispatch.actions.viewActions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-06.
 */


public class RequestInventoryAction extends Action {
    public RequestInventoryAction(ActionConsumer callback) {
        super(callback);
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
    }
}
