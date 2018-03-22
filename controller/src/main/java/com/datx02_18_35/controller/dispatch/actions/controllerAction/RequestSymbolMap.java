package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by raxxor on 2018-03-22.
 */

public class RequestSymbolMap extends Action {
    ActionConsumer callback;

    RequestSymbolMap(ActionConsumer callback){
        this.callback=callback;
    }

}
