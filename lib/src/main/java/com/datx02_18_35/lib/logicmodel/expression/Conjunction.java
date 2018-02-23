package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public class Conjunction extends Operator {

    Conjunction(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    @Override
    public int calculateHash() {
        long magic = ExpressionUtil.HASH_CONJ_MAGIC_NUMBER;
        long hash = magic;
        magic *= ExpressionUtil.HASH_CONJ_MAGIC_NUMBER; // Varför detta?? (Svar: Polynom ftw /Robin)
        hash += operand1.hashCode() * magic;
        magic *= ExpressionUtil.HASH_CONJ_MAGIC_NUMBER;
        hash += operand2.hashCode() * magic;
        return (int)hash;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Conjunction
                && this.operand1.equals(((Conjunction) other).operand1)
                && this.operand2.equals(((Conjunction) other).operand2);
    }

    @Override
    public String toString(){
        return "(" + operand1.toString() + "/\\" + operand2.toString() + ")";
    }
}
