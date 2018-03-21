package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;

import java.util.List;

/**
 * Created by raxxor on 2018-03-11.
 */

public class RequestApplyRuleAction extends Action {

    public final Rule rule;

    public RequestApplyRuleAction(ActionConsumer callback, Rule rule) {
        super(callback);
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
        if (rule == null) {
            throw new IllegalArgumentException("rule can't be null");
        }
        this.rule=rule;
    }
}
