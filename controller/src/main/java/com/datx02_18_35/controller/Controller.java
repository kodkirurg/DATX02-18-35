package com.datx02_18_35.controller;

import com.datx02_18_35.controller.actions.Action;
import com.datx02_18_35.controller.actions.ActionConsumer;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by robin on 2018-03-01.
 */

public class Controller extends ActionConsumer {

    public Controller(ActionConsumer viewCallback) {
        registerCallback(viewCallback);
    }

    @Override
    public void handleAction(Action action) {

    }
}
