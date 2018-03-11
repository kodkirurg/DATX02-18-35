package com.datx02_18_35.controller;

import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestApplyRuleAction;
import com.datx02_18_35.controller.dispatch.actions.RequestExpressionSelectionAction;
import com.datx02_18_35.controller.dispatch.actions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.RequestRulesAction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jdk.nashorn.internal.ir.RuntimeNode;


/**
 * Created by robin on 2018-03-01.
 */

public class Controller extends ActionConsumer {

    public static final Controller singleton = new Controller();

    private GameManager game;
    private Session session;


    private Controller() {
        game = new GameManager();
        Level level = game.getLevels().get(0);
        try {
            session = game.startLevel(level);
        } catch (GameManager.LevelNotInListException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleAction(Action action) throws UnhandledActionException, InterruptedException {
        if (action instanceof RequestInventoryAction) {
            assert session != null;
            Action reply = new RefreshInventoryAction(session.getAssumptions(), session.getInventories());
            action.callback(reply);
        }
        else if (action instanceof RequestGameboardAction) {
            assert session != null;
            Action reply = new RefreshGameboardAction(session.getGameBoard());
            action.callback(reply);
        }
        else if (action instanceof RequestRulesAction) {
            assert session != null;
            RequestRulesAction rulesAction = (RequestRulesAction)action;
            Collection<Rule> rules = session.getLegalRules(rulesAction.expressions);
            Action reply = new RefreshRulesAction(rules);
            action.callback(reply);
        }
        else if (action instanceof RequestExpressionSelectionAction){
            RequestExpressionSelectionAction request =  (RequestExpressionSelectionAction) action;
            session.setSelection(request.list);
        }
        else if (action instanceof RequestApplyRuleAction){
            List<Expression> selected = session.getSelected();
            Rule rule = ((RequestApplyRuleAction) action).rule;
            // a few apply directly
            // 3 special cases
        }
        else {
            throw new UnhandledActionException(action);
        }
    }
}
