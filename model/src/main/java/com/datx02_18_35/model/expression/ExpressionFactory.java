package com.datx02_18_35.model.expression;

import java.util.ArrayList;
import java.util.Collection;


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

    public Absurdity createAbsurdity(){return new Absurdity();}

    public Expression createNegation(Expression expression){
        if(expression instanceof Negation){
            return ((Negation) expression).operand;
        }else {
            return new Negation(expression);
        }

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
                assert expr2 instanceof Implication;
                assert expr3 instanceof Implication;

                Disjunction disj = (Disjunction)expr1;
                Implication impl1 = (Implication)expr2;
                Implication impl2 = (Implication)expr3;

                assert disj.operand1.equals(impl1.operand1);
                assert disj.operand2.equals(impl2.operand1);
                assert impl1.operand2.equals(impl2.operand2);

                result.add(impl1.operand2);
                break;
            }
            case DISJUNCTION_INTRODUCTION:{
                assert rule.expressions.size() == 2;
                Expression expr2 = rule.expressions.get(1);

                result.add(createOperator(OperatorType.DISJUNCTION, expr1, expr2));
                break;
            }
            case ABSURDITY_ELIMINATION:
                assert rule.expressions.size()==2;
                assert rule.expressions.get(0) instanceof Absurdity;
                result.add(rule.expressions.get(1));
                break;
            /*
            case ABSURDITY_INTRODUCTION:
                assert rule.expressions.size()==2;
                Expression expr2 = rule.expressions.get(1);

                assert expr1 instanceof Negation;
                Negation negation = (Negation) expr1;

                assert negation.operand.equals(expr2);
                result.add(createAbsurdity());

            case LAW_OF_EXCLUDED_MIDDLE:
                assert rule.expressions.size()==1;
                result.add(createOperator(OperatorType.DISJUNCTION,expr1,createNegation(expr1)));
                break;
                */
            default:
                throw new IllegalArgumentException("Unknown rule type!");
        }
        return result;
    }





}
