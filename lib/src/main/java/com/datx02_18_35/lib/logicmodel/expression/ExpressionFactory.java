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
                expressionArray.add(((Operator)exprs[0]).operand2);
                return expressionArray;
            case IMPLICATION_INTRODUCTION:
                expressionArray.add(createOperator(OperatorType.IMPLICATION,exprs[0],exprs[1]));
                return expressionArray;
            case CONJUNCTION_ELIMINATION:
                expressionArray.add(((Operator)exprs[0]).operand1);
                expressionArray.add(((Operator)exprs[0]).operand2);
                return expressionArray;
            case CONJUNCTION_INTRODUCTION:
                expressionArray.add(createOperator(OperatorType.CONJUNCTION,exprs[0],exprs[1]));
                return expressionArray;
            case DISJUNCTION_INTRODUCTION:
                expressionArray.add(createOperator(OperatorType.DISJUNCTION,exprs[0],exprs[1]));
                return expressionArray;
            case DISJUNCTION_ELIMINATION:
                // Assuming conjunction card first, disjunction second
                expressionArray.add(((Implication)(((Conjunction)exprs[0]).operand1)).operand2);
            default:
                throw new IllegalArgumentException("Unknown rule type!");
        }
    }

    public Collection<RuleType> checkLegalRules(Expression... exprs){
        ArrayList<RuleType> legalRules = new ArrayList<RuleType>();
        switch(exprs.length){
            case 1:
                legalRules.add(RuleType.DISJUNCTION_INTRODUCTION);
                if(exprs[0] instanceof Conjunction){
                    legalRules.add(RuleType.CONJUNCTION_ELIMINATION);
                }
                return legalRules;
            case 2:
                legalRules.add(RuleType.CONJUNCTION_INTRODUCTION);
                if(exprs[0] instanceof Implication && ((Implication)exprs[0]).operand1.logicEquals(exprs[1])) {
                    legalRules.add(RuleType.IMPLICATION_ELIMINATION);
                }else if(exprs[1] instanceof Implication && ((Implication)exprs[1]).operand1.logicEquals(exprs[0])){
                    legalRules.add(RuleType.IMPLICATION_ELIMINATION);
                }
                if(exprs[0] instanceof Disjunction && exprs[1] instanceof Conjunction){
                    if(((Operator)exprs[1]).operand1 instanceof Implication && ((Operator)exprs[1]).operand2 instanceof Implication){
                        Expression rhsA = ((Implication) (((Conjunction)exprs[1]).operand1)).operand2;
                        Expression rhsB = ((Implication) (((Conjunction)exprs[1]).operand2)).operand2;

                        if(rhsA.logicEquals(rhsB)){
                            Expression lhsA = ((Implication) (((Conjunction)exprs[1]).operand1)).operand1;
                            Expression lhsB = ((Implication) (((Conjunction)exprs[1]).operand2)).operand1;
                            if(lhsA.logicEquals(((Disjunction)exprs[0]).operand1) && lhsB.logicEquals(((Disjunction)exprs[0]).operand2) ||
                                    lhsB.logicEquals(((Disjunction)exprs[0]).operand1) && lhsA.logicEquals(((Disjunction)exprs[0]).operand2) ){
                                legalRules.add(RuleType.DISJUNCTION_ELIMINATION);
                            }
                        }
                    }
                }else if(exprs[1] instanceof Disjunction && exprs[0] instanceof Conjunction) {
                    if (((Operator) exprs[0]).operand1 instanceof Implication && ((Operator) exprs[0]).operand2 instanceof Implication) {
                        Expression rhsA = ((Implication) (((Conjunction) exprs[0]).operand1)).operand2;
                        Expression rhsB = ((Implication) (((Conjunction) exprs[0]).operand2)).operand2;

                        if (rhsA.logicEquals(rhsB)) {
                            Expression lhsA = ((Implication) (((Conjunction) exprs[0]).operand1)).operand1;
                            Expression lhsB = ((Implication) (((Conjunction) exprs[0]).operand2)).operand1;
                            if (lhsA.logicEquals(((Disjunction) exprs[1]).operand1) && lhsB.logicEquals(((Disjunction) exprs[1]).operand2) ||
                                    lhsB.logicEquals(((Disjunction) exprs[1]).operand1) && lhsA.logicEquals(((Disjunction) exprs[1]).operand2)) {
                                legalRules.add(RuleType.DISJUNCTION_ELIMINATION);
                            }
                        }
                    }
                }
                return  legalRules;
            default:
                throw new IllegalArgumentException("Too many arguments");
        }

    }
}
