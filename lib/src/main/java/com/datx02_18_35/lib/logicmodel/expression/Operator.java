package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Operator extends Expression {
    protected final Expression operand1, operand2;
    Operator(Expression operand1, Expression operand2) {
        super();
        this.operand1 = operand1;
        this.operand2 = operand2;
        hash=calculateHash();
    }
    public  Expression getOperand1(){
        return this.operand1 ;
    }

    public Expression getOperand2(){
        return this.operand2;
    }


}
