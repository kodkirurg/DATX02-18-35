package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Expression {

    private final int hash;

    Expression() { hash = calculateHash();}

    @Override
    public final int hashCode() {
        return hash;
    }

    protected abstract int calculateHash();

    @Override
    public abstract boolean equals(Object other);

    public boolean logicEquals(Object other){
        return  this.equals(other);
    }

}
