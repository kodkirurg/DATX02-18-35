package com.datx02_18_35.controller.dispatch.actions.viewActions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.expression.Rule;

/**
 * Created by robin on 2018-03-07.
 */

public class OpenSandboxAction extends Action {

    public final Reason reason;
    public final Rule incompleteRule;

    public OpenSandboxAction(Reason reason, Rule incompleteRule) {
        if (reason == null) {
            throw new IllegalArgumentException("reason can't be null");
        }
        if (incompleteRule == null && reason != Reason.ASSUMPTION) {
            throw new IllegalArgumentException("incompleteRule can only be null when reason is assumption");
        }
        this.reason = reason;
        this.incompleteRule = incompleteRule;
    }

    public OpenSandboxAction(Reason reason) {
        this(reason, null);
    }

    public enum Reason {
        DISJUNCTION_INTRODUCTION,
        ABSURDITY_ELIMINATION,
        ASSUMPTION,
    }
}
