package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-07.
 */

public class ClosedSandboxAction implements Action {
    public final Expression expression;

    public ClosedSandboxAction(Expression expression) {
        this.expression = expression;
    }
}
