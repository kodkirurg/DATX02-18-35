package com.datx02_18_35.controller;

import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.ClosedSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RequestInventoryAction;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.Session;

import javax.naming.ldap.Control;


/**
 * Created by robin on 2018-03-01.
 */

public class Controller extends ActionConsumer {

    private Session session = new Session(Level.exampleLevel);
    public static final Controller singleton = new Controller();

    private Controller() {}

    @Override
    public void handleAction(Action action) throws UnhandledActionException, InterruptedException {
        if (action instanceof RequestInventoryAction) {
            assert session != null;
            Action reply = new RefreshInventoryAction(session.getAssumptions(), session.getInventorys());
            action.callback(reply);
        }
        else if (action instanceof ClosedSandboxAction) {
            assert session != null;
        }
        else {
            throw new UnhandledActionException(action);
        }
    }
}
