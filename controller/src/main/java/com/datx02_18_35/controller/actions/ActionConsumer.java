package com.datx02_18_35.controller.actions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by robin on 2018-03-01.
 */

public abstract class ActionConsumer {
    protected ActionConsumer callback;

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
                    break;
                }
            }
        }
    }

    public void start() {
        ActionScanner actionScanner = new ActionScanner();
        Thread thread = new Thread(actionScanner);
        thread.start();
    }

    public void stop() throws InterruptedException {
        sendAction(new StopAction());
    }

    public void registerCallback(ActionConsumer callback) {
        this.callback = callback;
    }
    public void sendAction(Action action) throws InterruptedException {
        actionQueue.put(action);
    }

    public abstract void handleAction(Action action);
}
