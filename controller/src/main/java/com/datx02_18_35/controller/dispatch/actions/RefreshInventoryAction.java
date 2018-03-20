package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Expression;

/**
 * Created by robin on 2018-03-01.
 */

/**
 * Model requests view to update inventory in sandbox Expressions
 */
public class RefreshInventoryAction extends Action {
    public final Iterable<Expression> assumptions;
    public final Iterable<Iterable<Expression>> inventories;
    public RefreshInventoryAction(
            Iterable<Expression> assumptions,
            Iterable<Iterable<Expression>> inventories) {
        if (assumptions == null) {
            throw new IllegalArgumentException("assumptions can't be null");
        }
        if (inventories == null) {
            throw new IllegalArgumentException("inventories can't be null");
        }
        this.assumptions = assumptions;
        this.inventories = inventories;
    }
}
