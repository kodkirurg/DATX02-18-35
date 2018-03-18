package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Expression;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by robin on 2018-03-13.
 */

public class ShowNewExpressionAction extends Action {
    public final Set<Expression> expressions;
    public ShowNewExpressionAction(Collection<Expression> expressions) {
        this.expressions = new HashSet<>(expressions);
    }
}
