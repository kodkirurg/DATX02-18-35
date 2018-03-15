package com.datx02_18_35.controller.dispatch.actions;

/**
 * Created by robin on 2018-03-15.
 */

public class SaveUserDataAction extends Action {
    public final byte[] userData;
    public SaveUserDataAction(byte[] userData) {
        this.userData = userData;
    }
}
