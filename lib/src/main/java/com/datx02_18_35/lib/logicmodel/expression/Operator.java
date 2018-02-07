package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Operator extends Expression {
    protected final Expression operand1, operand2;
    Operator(Expression operand1, Expression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }


}
