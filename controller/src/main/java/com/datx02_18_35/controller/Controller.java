package com.datx02_18_35.controller;

import com.datx02_18_35.controller.dispatch.IllegalActionException;
import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshCurrentLevelAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshHypothesisAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshSymbolMap;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestCloseScopeAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestCurrentLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestDeleteFromGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestHypothesisAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestMoveFromInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestSymbolMap;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshScopeLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestScopeLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.ClosedSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.OpenSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshLevelsAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestAbortSessionAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestApplyRuleAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestAssumptionAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestLevelsAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestRulesAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestStartNewSessionAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestStartNextLevelAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.SaveUserDataAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.ShowNewExpressionAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.VictoryConditionMetAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.rules.Rule;
import com.datx02_18_35.model.expression.ExpressionParseException;
import com.datx02_18_35.model.game.GameManager;
import com.datx02_18_35.model.game.IllegalGameStateException;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelParseException;
import com.datx02_18_35.model.level.LevelProgression;

import java.util.List;
import java.util.Map;


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
    public static void init(Map<String, String> configFiles, byte[] userData) throws LevelParseException, ExpressionParseException {
        singleton = new Controller(configFiles);
        if (userData != null) {
            singleton.game.loadUserData(userData);
        }
    }

    private GameManager game;
    private Controller(Map<String, String> configFiles) throws LevelParseException, ExpressionParseException {
        game = new GameManager(configFiles);
    }

    public synchronized boolean isSessionInProgress() {
        return game.isSessionInProgress();
    }

    @Override
    public void handleAction(Action action) throws GameException {
        if (action instanceof RequestStartNewSessionAction) {
            if(game.getSession()!=null){
                game.quitLevel();
            }
            Level level = ((RequestStartNewSessionAction) action).level;
            game.startLevel(level);

        }
        else if (action instanceof RequestCurrentLevelAction){
            Action reply = new RefreshCurrentLevelAction(game.getSession().getLevel());
            action.callback(reply);
        }
        else if(action instanceof RequestSymbolMap){
            Action reply = new RefreshSymbolMap(game.getSession().getLevel().expressionFactory.getSymbolMap());
            action.callback(reply);
        }
        else if (action instanceof RequestStartNextLevelAction) {
            game.assertSessionInProgress();
            game.startNextLevel();
            game.assertSessionInProgress();
        }
        else if (action instanceof RequestAbortSessionAction) {
            game.assertSessionInProgress();
            game.quitLevel();
        }
        else if (action instanceof RequestHypothesisAction) {
            game.assertSessionInProgress();
            action.callback(new RefreshHypothesisAction(game.getSession().getHypotheses()));
        }
        else if (action instanceof RequestInventoryAction) {
            game.assertSessionInProgress();
            action.callback(getRefreshInventoryAction());
        }
        else if (action instanceof RequestGameboardAction) {
            game.assertSessionInProgress();
            action.callback(getRefreshGameboardAction());
        }
        else if (action instanceof RequestDeleteFromGameboardAction) {
            game.assertSessionInProgress();
            RequestDeleteFromGameboardAction deleteAction = (RequestDeleteFromGameboardAction) action;
            game.getSession().removeExpressionFromGameBoard(deleteAction.expressions);
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
                case LAW_OF_EXCLUDED_MIDDLE: {
                    if (rule.expressions.get(0) == null) {
                        action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.LAW_OF_EXCLUDING_MIDDLE, rule));
                        return;
                    }
                }
            }
            List<Expression> newExpressions = game.getSession().applyRule(rule);
            action.callback(getRefreshInventoryAction());
            action.callback(getRefreshGameboardAction());
            action.callback(new ShowNewExpressionAction(newExpressions));
            action.callback(new RefreshScopeLevelAction(game.getSession().getScopeDepth()));
            if (game.getSession().checkWin()) {
                LevelProgression progression = game.getUserData().getProgression(game.getSession().getLevel());
                int previousScore;
                if (progression.completed) {
                    previousScore = progression.stepsApplied;
                }
                else {
                    previousScore = -1;
                }
                game.voidFinishLevel();
                int currentScore = game.getSession().getStepsApplied();
                action.callback(new VictoryConditionMetAction(game.getSession().getLevel().goal,currentScore, previousScore,game.hasNextLevel()));
                action.callback(new SaveUserDataAction(game.saveUserData()));
                Util.log("Level completed! previousScore="+previousScore+", currentScore="+currentScore);
            }
        }
        else if (action instanceof ClosedSandboxAction) {
            game.assertSessionInProgress();
            ClosedSandboxAction closedAction = (ClosedSandboxAction)action;
            Expression expression =closedAction.expression;
            switch (closedAction.openReason) {
                case LAW_OF_EXCLUDING_MIDDLE:
                case ABSURDITY_ELIMINATION:
                case DISJUNCTION_INTRODUCTION: {
                    Rule newRule = game.getSession().finishIncompleteRule(closedAction.incompleteRule, expression);
                    // Send action to itself to apply the new complete rule
                    // Use the callback supplied
                    this.handleAction(new RequestApplyRuleAction(closedAction.applyRuleCallback, newRule));
                }
                break;
                case ASSUMPTION: {
                    game.getSession().makeAssumption(expression);
                    action.callback(getRefreshInventoryAction());
                    action.callback(getRefreshGameboardAction());
                }
                break;
                default:
                    throw new IllegalActionException(action, "Unknown reason");
            }

        }
        else if (action instanceof RequestAssumptionAction) {
            game.assertSessionInProgress();
            action.callback(new OpenSandboxAction(OpenSandboxAction.Reason.ASSUMPTION));
        }
        else if (action instanceof RequestLevelsAction) {
            game.assertSessionNotInProgress();
            action.callback(new RefreshLevelsAction(game.getLevelCollection(), game.getProgressionMapReadOnly()));
        }
        else if (action instanceof RequestScopeLevelAction){
            game.assertSessionInProgress();
            action.callback(new RefreshScopeLevelAction(game.getSession().getScopeDepth()));
        }
        else if( action instanceof RequestMoveFromInventoryAction){
            game.assertSessionInProgress();
            game.getSession().addExpressionToGameBoard(((RequestMoveFromInventoryAction) action).expression);
            action.callback(getRefreshGameboardAction());
        }
        else if( action instanceof RequestCloseScopeAction){
            game.assertSessionInProgress();
            game.getSession().closeScope();
            action.callback(getRefreshGameboardAction());
            action.callback(getRefreshInventoryAction());
            action.callback(new RefreshScopeLevelAction(game.getSession().getScopeDepth()));
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
    private Action getRefreshHypothesisAction() throws IllegalGameStateException {
        return new RefreshHypothesisAction(game.getSession().getHypotheses());
    }
}
