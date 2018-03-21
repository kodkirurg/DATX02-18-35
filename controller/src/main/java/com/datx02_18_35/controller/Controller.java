package com.datx02_18_35.controller;

import com.datx02_18_35.controller.dispatch.IllegalActionException;
import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.ClosedSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.OpenSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshLevelsAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestAbortSessionAction;
import com.datx02_18_35.controller.dispatch.actions.RequestApplyRuleAction;
import com.datx02_18_35.controller.dispatch.actions.RequestAssumptionAction;
import com.datx02_18_35.controller.dispatch.actions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RequestLevelsAction;
import com.datx02_18_35.controller.dispatch.actions.RequestRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestStartNewSessionAction;
import com.datx02_18_35.controller.dispatch.actions.SaveUserDataAction;
import com.datx02_18_35.controller.dispatch.actions.ShowNewExpressionAction;
import com.datx02_18_35.controller.dispatch.actions.VictoryConditionMetAction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.IllegalGameStateException;
import com.datx02_18_35.model.game.IllegalRuleException;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.LevelParseException;
import com.datx02_18_35.model.game.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by robin on 2018-03-01.
 */

public class Controller extends ActionConsumer {

    private static Controller singleton = null;
    public static Controller getSingleton() {
        if (singleton == null) {
            throw new IllegalStateException("Singleton not initialized. Call init first.");
        }
        return singleton;
    }
    public static void init(List<String> levelStrings, byte[] userData) throws LevelParseException {
        singleton = new Controller(levelStrings);
        if (userData != null) {
            singleton.game.loadUserData(userData);
        }
    }

    public List<Level> getLevels() {
        return game.getLevels();
    }

    private GameManager game;
    private Controller(List<String> levelStrings) throws LevelParseException {
        game = new GameManager(levelStrings);
    }

    public synchronized boolean isSessionInProgress() {
        return game.isSessionInProgress();
    }

    @Override
    protected void handleAction(Action action)
            throws
            UnhandledActionException,
            IllegalActionException,
            InterruptedException,
            IllegalGameStateException,
            GameManager.LevelNotInListException {
        if (action instanceof RequestStartNewSessionAction) {
            game.assertSessionNotInProgress();
            Level level = ((RequestStartNewSessionAction) action).level;
            game.startLevel(level);
        }
        else if (action instanceof RequestAbortSessionAction) {
            game.assertSessionInProgress();
            game.quitLevel(false);
        }
        else if (action instanceof RequestInventoryAction) {
            game.assertSessionInProgress();
            action.callback(getRefreshInventoryAction());
        }
        else if (action instanceof RequestGameboardAction) {
            game.assertSessionInProgress();
            action.callback(getRefreshGameboardAction());
        }
        else if (action instanceof RequestRulesAction) {
            game.assertSessionInProgress();
            RequestRulesAction rulesAction = (RequestRulesAction) action;
            List<Rule> rules = game.getSession().getLegalRules(rulesAction.expressions);
            Action reply = new RefreshRulesAction(rules);
            action.callback(reply);
        }
        else if (action instanceof RequestApplyRuleAction) {
            game.assertSessionInProgress();
            Rule rule = ((RequestApplyRuleAction) action).rule;
            switch (rule.type) {
                case ABSURDITY_ELIMINATION: {
                    if (rule.expressions.get(1) == null) {
                        action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.ABSURDITY_ELIMINATION, rule));
                        return;
                    }
                }
                break;
                case DISJUNCTION_INTRODUCTION: {
                    if (rule.expressions.get(0) == null || rule.expressions.get(1) == null) {
                        action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.DISJUNCTION_INTRODUCTION, rule));
                        return;
                    }
                }
                break;
            }
            List<Expression> newExpressions = game.getSession().applyRule(rule);
            action.callback(getRefreshInventoryAction());
            action.callback(getRefreshGameboardAction());
            action.callback(new ShowNewExpressionAction(newExpressions));
            if (game.getSession().checkWin()) {
                game.quitLevel(true);
                action.callback(new VictoryConditionMetAction());
                action.callback(new SaveUserDataAction(game.saveUserData()));
            }
        }
        else if (action instanceof ClosedSandboxAction) {
            game.assertSessionInProgress();
            ClosedSandboxAction closedAction = (ClosedSandboxAction)action;
            Expression expression =closedAction.expression;
            switch (closedAction.openReason) {
                case ABSURDITY_ELIMINATION:
                case DISJUNCTION_INTRODUCTION: {
                    Rule newRule = game.getSession().finishIncompleteRule(closedAction.incompleteRule, expression);
                    // Send action to itself to apply the new complete rule
                    // Use the callback supplied
                    this.sendAction(new RequestApplyRuleAction(closedAction.applyRuleCallback, newRule));
                }
                break;
                case ASSUMPTION: {
                    game.getSession().makeAssumption(expression);
                    action.callback(getRefreshInventoryAction());
                    action.callback(getRefreshGameboardAction());
                }
                break;
            }

        }
        else if (action instanceof RequestAssumptionAction) {
            game.assertSessionInProgress();
            action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.ASSUMPTION));
        }
        else if (action instanceof RequestLevelsAction) {
            game.assertSessionNotInProgress();
            action.callback(new RefreshLevelsAction(game.getLevelList()));
        }
        else {
            throw new UnhandledActionException(action);
        }
    }

    private Action getRefreshInventoryAction() throws IllegalGameStateException {
        return new RefreshInventoryAction(game.getSession().getAssumptions(), game.getSession().getInventories());
    }

    private Action getRefreshGameboardAction() throws IllegalGameStateException {
        return new RefreshGameboardAction(game.getSession().getGameBoard());
    }
}
