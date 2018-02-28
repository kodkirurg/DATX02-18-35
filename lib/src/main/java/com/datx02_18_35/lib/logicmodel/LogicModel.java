package com.datx02_18_35.lib.logicmodel;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.OperatorType;
import com.datx02_18_35.lib.logicmodel.expression.RuleType;
import com.datx02_18_35.lib.logicmodel.game.Session;

import java.util.ArrayList;

public class LogicModel {
    public static void main(String[] args) {
        ExpressionFactory exprFact = ExpressionFactory.getSingleton();
        Expression p1 = exprFact.createProposition("P");
        Expression q1 = exprFact.createProposition("Q");
        Expression qp1 = exprFact.createOperator(OperatorType.IMPLICATION,q1,p1);
        ArrayList<Expression> hypo = new ArrayList<>();
        Expression goal = qp1;
        hypo.add(p1);


        Session session = new Session(hypo,goal);


        ExpressionParser expressionParser = new ExpressionParser();
        String stringExpression = "((((P)|(Q))&((Q)>(T)))&(P))";
        Expression expression = expressionParser.parseString(stringExpression);
        System.out.println(expression.toString());

        //TestSuite testSuite = new TestSuite(exprFact,session);
        // testSuite.makeMove();

       /* Expression p1 = exprFact.createProposition("P");
        Expression p2 = exprFact.createProposition("P");
        Expression q1 = exprFact.createProposition("Q");
        Expression q2 = exprFact.createProposition("Q");
        Expression r1 = exprFact.createProposition("R");
        Expression s1 = exprFact.createProposition("S");
        Expression c1 = exprFact.createOperator(OperatorType.CONJUNCTION, p1, q1);
        Expression c2 = exprFact.createOperator(OperatorType.CONJUNCTION, p2, q2);
        Expression c3 = exprFact.createOperator(OperatorType.CONJUNCTION, q2, p2);
        Expression c4 = exprFact.createOperator(OperatorType.IMPLICATION, p1,q1);
        Expression c5 = exprFact.createOperator(OperatorType.IMPLICATION, r1,q1);
        Expression c6 = exprFact.createOperator(OperatorType.DISJUNCTION, p1,r1);
        Expression c7 = exprFact.createOperator(OperatorType.CONJUNCTION, c4,c5);
        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());
        System.out.println(c1.equals(c2));

        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(q2));

        //col.forEach((type) -> System.out.println(type));
       // for (RuleType type : col1) {
        //    System.out.println(type);
        //}
        //System.out.println(list.get(0)+""+ list.get(1));
	    System.out.println(c1.equals(c3));
        System.out.println(c1.equals(c3));


       // for (RuleType type : col2) {
        //    System.out.println(type);
        //}
    */
    }
}
