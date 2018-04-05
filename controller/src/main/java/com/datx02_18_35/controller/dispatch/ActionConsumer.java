package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.actions.StopAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.IllegalGameStateException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by robin on 2018-03-01.
 */

public abstract class ActionConsumer {


    /**
     * Implemented by the specific service, throw UnhandledActionException if an unhandled Action is supplied.
     * @param action
     * @throws UnhandledActionException
     * @throws InterruptedException
     */
    public abstract void handleAction(Action action) throws GameException;
}
