package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-01.
 */

public class RefreshInventoryAction extends Action {
    public final Iterable<Expression> assumptions;
    public final Iterable<Iterable<Expression>> inventories;
    public RefreshInventoryAction(
            Iterable<Expression> assumptions,
            Iterable<Iterable<Expression>> inventories) {
        this.assumptions = assumptions;
        this.inventories = inventories;
    }
}
