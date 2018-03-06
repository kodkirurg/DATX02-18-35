package com.datx02_18_35.model.expression;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Expression {

    protected int hash;

    @Override
    public final int hashCode() {
        return hash;
    }

    protected abstract int calculateHash();

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();


}
