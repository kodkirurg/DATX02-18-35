package com.datx02_18_35.lib.logicmodel;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;

import java.util.ArrayList;
import java.util.Collection;

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
        Expression c3 = exprFact.createOperator(ExpressionFactory.OperatorType.CONJUNCTION, q2, p2);


        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());
        System.out.println(c1.equals(c2));

        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(q2));
        Collection<ExpressionFactory.RuleType> col = exprFact.checkLegalRules(c1);
        //col.forEach((type) -> System.out.println(type));
        for (ExpressionFactory.RuleType type : col) {
            System.out.println(type);
        }
        //System.out.println(list.get(0)+""+ list.get(1));
	    System.out.println(c1.equals(c3));
        System.out.println(c1.logicEquals(c3));

    }
}
