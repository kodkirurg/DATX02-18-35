package com.datx02_18_35.controller;

import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestApplyRuleAction;
import com.datx02_18_35.controller.dispatch.actions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RequestRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestStartNewSession;
import com.datx02_18_35.controller.dispatch.actions.ShowNewExpressionAction;
import com.datx02_18_35.controller.dispatch.actions.VictoryConditionMetAction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.Session;

import java.util.Collection;


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
    public void handleAction(Action action) throws UnhandledActionException, InterruptedException {
        if (action instanceof RequestStartNewSession) {
            assert session == null;
            Level level = ((RequestStartNewSession) action).level;
            try {
                session = game.startLevel(level);
            } catch (GameManager.LevelNotInListException e) {
                e.printStackTrace();
                return;
            }
            action.callback(getRefreshInventoryAction());
            action.callback(getRefreshGameboardAction());
        }
        else if (action instanceof RequestInventoryAction) {
            assert session != null;
            action.callback(getRefreshInventoryAction());
        }
        else if (action instanceof RequestGameboardAction) {
            assert session != null;
            action.callback(getRefreshGameboardAction());
        }
        else if (action instanceof RequestRulesAction) {
            assert session != null;
            RequestRulesAction rulesAction = (RequestRulesAction) action;
            Collection<Rule> rules = session.getLegalRules(rulesAction.expressions);
            Action reply = new RefreshRulesAction(rules);
            action.callback(reply);
        }
        else if (action instanceof RequestApplyRuleAction) {
            Rule rule = ((RequestApplyRuleAction) action).rule;
            Collection<Expression> newExpressions = session.applyRule(rule);
            action.callback(getRefreshInventoryAction());
            action.callback(getRefreshGameboardAction());
            action.callback(new ShowNewExpressionAction(newExpressions));
            if (session.checkWin()) {
                action.callback(new VictoryConditionMetAction());
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
