package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;

import java.util.List;

/**
 * Created by raxxor on 2018-03-11.
 */

public class RequestApplyRuleAction extends Action {

    public final List<Expression> list;
    public final Rule rule;

    public RequestApplyRuleAction(ActionConsumer callback, Rule rule, List<Expression> list) {
        super(callback);
        this.list=list;
        this.rule=rule;
    }
}
