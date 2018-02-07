package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public class Implication extends Operator {
    Implication(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    @Override
    public int hashCode() {
        long magic = ExpressionUtil.HASH_IMPL_MAGIC_NUMBER;
        long hash = magic;
        magic *= ExpressionUtil.HASH_IMPL_MAGIC_NUMBER;
        hash += operand1.hashCode() * magic;
        magic *= ExpressionUtil.HASH_IMPL_MAGIC_NUMBER;
        hash += operand2.hashCode() * magic;
        return (int)hash;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Implication
                && this.operand1.equals(((Implication) other).operand1)
                && this.operand2.equals(((Implication) other).operand2);
    }
}
