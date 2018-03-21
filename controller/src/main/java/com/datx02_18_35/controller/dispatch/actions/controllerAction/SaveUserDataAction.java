package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;

/**
 * Created by robin on 2018-03-15.
 */

public class SaveUserDataAction extends Action {
    public final byte[] userData;
    public SaveUserDataAction(byte[] userData) {
        if (userData == null) {
            throw new IllegalArgumentException("userData can't be null");
        }
        this.userData = userData;
    }
}
