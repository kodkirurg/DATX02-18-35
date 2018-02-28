package com.datx02_18_35.lib.logicmodel;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.OperatorType;

/**
 * Created by Jonatan on 2018-02-28.
 */

public class ExpressionParser {
    ExpressionFactory exprFactory;

    public ExpressionParser(){
        exprFactory=ExpressionFactory.getSingleton();
    }
    public Expression parseString(String expression){
        Expression leftexpression;
        Expression rightexpression;
        expression=expression.substring(1,expression.length()-1);
        int parenthesisCount=0;
        for(int i=0; i<expression.length(); i++){
            char c=expression.charAt(i);
            switch (c){
                case '(':
                    parenthesisCount++;
                    break;
                case')':
                    parenthesisCount--;
                    break;
                case '&': //CONJUNCTION
                    if(parenthesisCount==0){
                        String leftString = expression.substring(0,i);
                        String rightString = expression.substring(i+1);
                        return exprFactory.createOperator(OperatorType.CONJUNCTION,parseString(leftString),parseString(rightString));
                    }
                    break;
                case '|': //DISJUNCTION
                    if(parenthesisCount==0){
                        String leftString = expression.substring(0,i);
                        String rightString = expression.substring(i+1);
                        return exprFactory.createOperator(OperatorType.DISJUNCTION,parseString(leftString),parseString(rightString));
                    }
                    break;
                case '>': //IMPLICATION
                    if(parenthesisCount==0){
                        String leftString = expression.substring(0,i);
                        String rightString = expression.substring(i+1);
                        return exprFactory.createOperator(OperatorType.IMPLICATION,parseString(leftString),parseString(rightString));
                    }
                    break;
                case '#': //ABSURDITY
                    if(parenthesisCount==0) {
                        return exprFactory.createAbsurdity();
                    }
                    break;
                case '!': //NEGATION
                    if(parenthesisCount == 0){
                        String rightString = expression.substring(i+1);
                        return  exprFactory.createNegation(parseString(rightString));
                    }
                    break;
                default: //PROPOSITION
                    if(parenthesisCount==0) {
                        return exprFactory.createProposition(Character.toString(c));
                    }
                    break;

            }
        }
        throw new IllegalArgumentException("Emtpy Expression Not Allowed As Argument");
    }
}
