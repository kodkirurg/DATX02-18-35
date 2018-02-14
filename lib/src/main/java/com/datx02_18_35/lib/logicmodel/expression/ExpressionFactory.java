package com.datx02_18_35.lib.logicmodel.expression;


import java.util.ArrayList;
import java.util.Collection;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by robin on 2018-02-07.
 */

public class ExpressionFactory {

    private static final ExpressionFactory singleton = new ExpressionFactory();

    private ExpressionFactory() {

    }

    public static ExpressionFactory getSingleton() {
        return singleton;
    }

    public enum OperatorType {
        CONJUNCTION,
        DISJUNCTION,
        IMPLICATION,
    }

    public enum RuleType {
        IMPLICATION_ELIMINATION,
        IMPLICATION_INTRODUCTION,
        CONJUNCTION_ELIMINATION,
        CONJUNCTION_INTRODUCTION,
        DISJUNCTION_ELIMINATION,
        DISJUNCTION_INTRODUCTION,

    }

    public Proposition createProposition(String id) {
        return new Proposition(id);
    }

    public Operator createOperator(
            OperatorType type, Expression operand1, Expression operand2) {
        switch (type) {
            case CONJUNCTION:
                return new Conjunction(operand1, operand2);
            case DISJUNCTION:
                return new Disjunction(operand1, operand2);
            case IMPLICATION:
                return new Implication(operand1, operand2);
            default:
                throw new IllegalArgumentException("Unknown operator type!");
        }
    }

    public Collection<Expression> applyRule(RuleType type, Expression... exprs) {
        ArrayList<Expression> expressionArray = new ArrayList<Expression>();
        switch (type) {
            case IMPLICATION_ELIMINATION:
                expressionArray.add(((Operator)exprs[0]).getOperand2());
                return expressionArray;
            case IMPLICATION_INTRODUCTION:
                expressionArray.add(createOperator(OperatorType.IMPLICATION,exprs[0],exprs[1]));
                return expressionArray;
            case CONJUNCTION_ELIMINATION:
                expressionArray.add(((Operator)exprs[0]).getOperand1());
                expressionArray.add(((Operator)exprs[0]).getOperand2());
                return expressionArray;
            case CONJUNCTION_INTRODUCTION:
                expressionArray.add(createOperator(OperatorType.CONJUNCTION,exprs[0],exprs[1]));
                return expressionArray;
            case DISJUNCTION_INTRODUCTION:
                expressionArray.add(createOperator(OperatorType.DISJUNCTION,exprs[0],exprs[1]));
                return expressionArray;
            case DISJUNCTION_ELIMINATION:
                throw new NotImplementedException();
            default:
                throw new IllegalArgumentException("Unknown rule type!");
        }
    }



}
