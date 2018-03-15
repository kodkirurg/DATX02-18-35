package com.datx02_18_35.controller.dispatch;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.actions.StopAction;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.IllegalGameStateException;
import com.datx02_18_35.model.game.IllegalRuleException;

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
                } catch (IllegalActionException e) {
                    e.printStackTrace();
                    break;
                } catch (GameManager.LevelNotInListException e) {
                    e.printStackTrace();
                    break;
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * Start the handling service
     */
    public synchronized void start() {
        assert !started;
        started = true;
        ActionScanner actionScanner = new ActionScanner();
        Thread thread = new Thread(actionScanner);
        thread.start();
    }

    /**
     * Stop the handling service
     * @throws InterruptedException
     */
    public synchronized void stop() throws InterruptedException {
        assert started;
        sendAction(new StopAction());
    }

    /**
     * Used to send an action to this consumer
     * @param action
     * @throws InterruptedException
     */
    public synchronized void sendAction(Action action) throws InterruptedException {
        actionQueue.put(action);
    }


    /**
     * Implemented by the specific service, throw UnhandledActionException if an unhandled Action is supplied.
     * @param action
     * @throws UnhandledActionException
     * @throws InterruptedException
     */
    protected abstract void handleAction(Action action) throws UnhandledActionException, InterruptedException, IllegalActionException, IllegalGameStateException, GameManager.LevelNotInListException;
}
