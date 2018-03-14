package com.datx02_18_35.controller;

import com.datx02_18_35.controller.dispatch.IllegalActionException;
import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.ClosedSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.OpenSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestAbortSessionAction;
import com.datx02_18_35.controller.dispatch.actions.RequestApplyRuleAction;
import com.datx02_18_35.controller.dispatch.actions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RequestRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestStartNewSessionAction;
import com.datx02_18_35.controller.dispatch.actions.ShowNewExpressionAction;
import com.datx02_18_35.controller.dispatch.actions.VictoryConditionMetAction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.IllegalRuleException;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by robin on 2018-03-01.
 */

public class Controller extends ActionConsumer {

    public static final Controller singleton = new Controller();

    private GameManager game;
    private Session session;


    private Controller() {
        game = new GameManager();
        session = null;
    }

    @Override
    public void handleAction(Action action)
            throws UnhandledActionException, IllegalActionException, InterruptedException, IllegalRuleException {
        if (action instanceof RequestStartNewSessionAction) {
            if (session != null) {
                throw new IllegalActionException(action);
            }
            Level level = ((RequestStartNewSessionAction) action).level;
            try {
                session = game.startLevel(level);
            } catch (GameManager.LevelNotInListException e) {
                e.printStackTrace();
                return;
            }
            action.callback(getRefreshInventoryAction());
            action.callback(getRefreshGameboardAction());
        }
        else if (action instanceof RequestAbortSessionAction) {
            if (session != null) {
                throw new IllegalActionException(action);
            }
            session = null;
        }
        else if (action instanceof RequestInventoryAction) {
            if (session == null) {
                throw new IllegalActionException(action);
            }
            action.callback(getRefreshInventoryAction());
        }
        else if (action instanceof RequestGameboardAction) {
            if (session == null) {
                throw new IllegalActionException(action);
            }
            action.callback(getRefreshGameboardAction());
        }
        else if (action instanceof RequestRulesAction) {
            if (session == null) {
                throw new IllegalActionException(action);
            }
            RequestRulesAction rulesAction = (RequestRulesAction) action;
            Collection<Rule> rules = session.getLegalRules(rulesAction.expressions);
            Action reply = new RefreshRulesAction(rules);
            action.callback(reply);
        }
        else if (action instanceof RequestApplyRuleAction) {
            if (session == null) {
                throw new IllegalActionException(action);
            }
            Rule rule = ((RequestApplyRuleAction) action).rule;
            switch (rule.type) {
                case ABSURDITY_ELIMINATION: {
                    if (rule.expressions.get(1) == null) {
                        action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.ABSURDITY_ELIMINATION, rule));
                        return;
                    }
                }
                case DISJUNCTION_INTRODUCTION: {
                    if (rule.expressions.get(0) == null || rule.expressions.get(1) == null) {
                        action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.DISJUNCTION_INTRODUCTION, rule));
                        return;
                    }
                }
            }
            List<Expression> newExpressions = session.applyRule(rule);
            action.callback(getRefreshInventoryAction());
            action.callback(getRefreshGameboardAction());
            action.callback(new ShowNewExpressionAction(newExpressions));
            if (session.checkWin()) {
                session = null;
                action.callback(new VictoryConditionMetAction());
            }
        }
        else if (action instanceof ClosedSandboxAction) {
            ClosedSandboxAction closedAction = (ClosedSandboxAction)action;
            OpenSandboxAction openAction = closedAction.openAction;
            Expression expression =closedAction.expression;
            switch (openAction.reason) {
                case ABSURDITY_ELIMINATION:
                case DISJUNCTION_INTRODUCTION: {
                    Rule newRule = session.finishIncompleteRule(openAction.incompleteRule, expression);
                    // Send action to itself to apply the new complete rule
                    // Use the callback supplied
                    this.sendAction(new RequestApplyRuleAction(closedAction.applyRuleCallback, newRule));
                }
                break;
                case ASSUMPTION: {
                    session.makeAssumption(expression);
                    action.callback(getRefreshInventoryAction());
                    action.callback(getRefreshGameboardAction());
                }
                break;
            }

        }
        else {
            throw new UnhandledActionException(action);
        }
    }

    private Action getRefreshInventoryAction() {
        return new RefreshInventoryAction(session.getAssumptions(), session.getInventories());
    }

    private Action getRefreshGameboardAction() {
        return new RefreshGameboardAction(session.getGameBoard());
    }
}
