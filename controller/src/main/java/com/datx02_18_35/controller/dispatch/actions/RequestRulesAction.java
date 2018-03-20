package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.model.expression.Expression;

import java.util.List;

/**
 * Created by robin on 2018-03-08.
 */

public class RequestRulesAction extends Action {

    public final List<Expression> expressions;

    public RequestRulesAction(ActionConsumer callback, List<Expression> expressions) {
        super(callback);
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
        if (expressions == null) {
            throw new IllegalArgumentException("expressions can't be null");
        }
        this.expressions = expressions;
    }
}
