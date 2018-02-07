package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Expression {
    Expression() {

    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object other);
}
