package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by Johan on 2018-02-28.
 */

public class Negation extends Expression {
    Expression operand;

    Negation(Expression operand){
        super();
        this.operand = operand;
        hash=calculateHash();

    }
    @Override
    protected int calculateHash() {
        return (int)(ExpressionUtil.HASH_NEGA_MAGIC_NUMBER*operand.calculateHash());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Negation && operand.equals(((Negation) other).operand);
    }

    @Override
    public String toString() {
        return "(!"+ this.operand.toString() + ")";
    }
}
