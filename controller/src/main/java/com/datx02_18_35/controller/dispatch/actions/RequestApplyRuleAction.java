package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
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
        this.rule=rule;
    }
}
