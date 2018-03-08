package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-07.
 */

public class ClosedSandboxAction extends Action {
    public final Expression expression;

    public ClosedSandboxAction(ActionConsumer callback, Expression expression) {
        super(callback);
        this.expression = expression;
    }
}
