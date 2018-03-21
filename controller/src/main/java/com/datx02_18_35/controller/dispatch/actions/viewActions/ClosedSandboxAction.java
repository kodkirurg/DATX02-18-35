package com.datx02_18_35.controller.dispatch.actions.viewActions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;

/**
 * Created by robin on 2018-03-14.
 */

public class ClosedSandboxAction extends Action {
    public final Expression expression;
    public final ActionConsumer applyRuleCallback;
    public final OpenSandboxAction.Reason openReason;
    public final Rule incompleteRule;

    public ClosedSandboxAction(ActionConsumer callback, OpenSandboxAction openAction, Expression expression) {
        super(callback);
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
        if (openAction == null) {
            throw new IllegalArgumentException("openAction can't be null");
        }
        if (expression == null) {
            throw new IllegalArgumentException("expression can't be null");
        }
        this.applyRuleCallback = callback;
        this.expression = expression;
        this.openReason = openAction.reason;
        this.incompleteRule = openAction.incompleteRule;
    }
}
