package com.datx02_18_35.lib.logicmodel.expression;


import com.sun.istack.internal.Pool;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


    /**
     * Warning: This function assumes the Rule object is created automatically and correct
     * @param rule
     * @return
     */
    public Collection<Expression> applyRule(Rule rule) {
        Collection<Expression> result = new ArrayList<>();
        assert rule.expressions.size() >= 1;
        Expression expr1 = rule.expressions.get(0);

        switch (rule.type) {
            case IMPLICATION_ELIMINATION: {
                assert rule.expressions.size() == 2;
                Expression expr2 = rule.expressions.get(1);

                assert expr1 instanceof Implication;
                Implication impl = (Implication) expr1;

                assert impl.operand1.equals(expr2);
                result.add(impl.operand2);

                break;
            }
            case IMPLICATION_INTRODUCTION: {
                assert rule.expressions.size() == 2;
                Expression expr2 = rule.expressions.get(1);

                result.add(createOperator(OperatorType.IMPLICATION, expr1, expr2));
                break;
            }
            case CONJUNCTION_ELIMINATION: {
                assert rule.expressions.size() == 1;

                assert expr1 instanceof Conjunction;
                Conjunction conj = (Conjunction) expr1;

                result.add(conj.operand1);
                result.add(conj.operand2);
                break;
            }
            case CONJUNCTION_INTRODUCTION: {
                assert rule.expressions.size() == 2;
                Expression expr2 = rule.expressions.get(1);

                result.add(createOperator(OperatorType.CONJUNCTION, expr1, expr2));
                break;
            }
            case DISJUNCTION_ELIMINATION: {
                assert rule.expressions.size() == 3;
                Expression expr2 = rule.expressions.get(1);
                Expression expr3 = rule.expressions.get(2);

                assert expr1 instanceof Disjunction;
                //TODO: finish :)
                throw new NotImplementedException();
            }
            case DISJUNCTION_INTRODUCTION:

                throw new NotImplementedException();
            default:
                throw new IllegalArgumentException("Unknown rule type!");
        }
        return result;
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
                throw new IllegalArgumentException("To many arguments");
        }

    }
}
