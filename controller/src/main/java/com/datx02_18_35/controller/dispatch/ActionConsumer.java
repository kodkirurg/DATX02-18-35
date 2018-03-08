package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.actions.StopAction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by robin on 2018-03-01.
 */

public abstract class ActionConsumer {

    private boolean started = false;
    private final BlockingQueue<Action> actionQueue = new LinkedBlockingQueue<>();

    private class ActionScanner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Action action = actionQueue.take();
                    if (action instanceof StopAction) {
                        break;
                    }
                    handleAction(action);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } catch (UnhandledActionException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public synchronized void start() {
        assert !started;
        started = true;
        ActionScanner actionScanner = new ActionScanner();
        Thread thread = new Thread(actionScanner);
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        assert started;
        sendAction(new StopAction());
    }

    public synchronized void sendAction(Action action) throws InterruptedException {
        actionQueue.put(action);
    }



    public abstract void handleAction(Action action) throws UnhandledActionException, InterruptedException;
}
