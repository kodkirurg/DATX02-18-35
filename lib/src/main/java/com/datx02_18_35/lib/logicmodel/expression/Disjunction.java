package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public class Disjunction extends Operator {

    Disjunction(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    @Override
    public int hashCode() {
        long magic = ExpressionUtil.HASH_DISJ_MAGIC_NUMBER;
        long hash = magic;
        magic *= ExpressionUtil.HASH_DISJ_MAGIC_NUMBER;
        hash += operand1.hashCode() * magic;
        magic *= ExpressionUtil.HASH_DISJ_MAGIC_NUMBER;
        hash += operand2.hashCode() * magic;
        return (int)hash;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Disjunction
                && this.operand1.equals(((Disjunction) other).operand1)
                && this.operand2.equals(((Disjunction) other).operand2);
    }
}
