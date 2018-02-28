package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by Johan on 2018-02-28.
 */

public class Negation extends Expression {
    Expression expression;

    Negation(Expression expression){
        super();
        this.expression = expression;
        hash=calculateHash();

    }
    @Override
    protected int calculateHash() {
        return (int)(ExpressionUtil.HASH_NEGA_MAGIC_NUMBER*expression.calculateHash());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Negation && expression.equals(((Negation) other).expression);
    }

    @Override
    public String toString() {
        return "(!"+ this.expression.toString() + ")";
    }
}
