package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public class Proposition extends Expression {

    private final String id;

    Proposition(String id) {
        this.id = id;
    }

    @Override
    public int calculateHash() {
        return (int)(id.hashCode() * ExpressionUtil.HASH_PROP_MAGIC_NUMBER);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Proposition && id.equals(((Proposition) other).id);
    }
}
