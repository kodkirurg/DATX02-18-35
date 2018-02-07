package com.datx02_18_35.lib.logicmodel;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;

public class LogicModel {
    public static void main(String[] args) {
        System.out.println("Hello from logic model library!");

        ExpressionFactory exprFact = ExpressionFactory.getSingleton();
        Expression p1 = exprFact.createProposition("P");
        Expression p2 = exprFact.createProposition("P");
        Expression q1 = exprFact.createProposition("Q");
        Expression q2 = exprFact.createProposition("Q");
        Expression c1 = exprFact.createOperator(ExpressionFactory.OperatorType.CONJUNCTION, p1, q1);
        Expression c2 = exprFact.createOperator(ExpressionFactory.OperatorType.CONJUNCTION, p2, q2);

        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c1.equals(c2));
    }
}
