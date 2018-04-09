package com.datx02_18_35.model.expression;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Operator extends Expression {
    protected final Expression operand1, operand2;
    Operator(Expression operand1, Expression operand2) {
        super();
        assert operand1 != null;
        assert operand2 != null;
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

    protected abstract String getOperatorSymbol();

    @Override
    public String toString() {
        String op1Str = operand1.toString();
        String op2Str = operand2.toString();
        if (operand1 instanceof Operator) {
            op1Str = "(" + op1Str + ")";
        }
        if (operand2 instanceof Operator) {
            op2Str = "(" + op2Str + ")";
        }
        return op1Str + " " + getOperatorSymbol() + " " + op2Str;
    }

}
