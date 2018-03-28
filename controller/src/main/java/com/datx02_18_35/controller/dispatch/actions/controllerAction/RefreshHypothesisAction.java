package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-26.
 */

public class RefreshHypothesisAction extends Action {
    public final Iterable<Expression> hypothesis;

    public RefreshHypothesisAction(Iterable<Expression> hypothesis) {
        this.hypothesis = hypothesis;
    }

}
