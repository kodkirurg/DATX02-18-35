package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-14.
 */

public class ClosedSandboxAction extends Action {
    public final OpenSandboxAction openAction;
    public final Expression expression;

    ClosedSandboxAction(ActionConsumer callback, OpenSandboxAction openAction, Expression expression) {
        super(callback);
        this.openAction = openAction;
        this.expression = expression;
    }
}
