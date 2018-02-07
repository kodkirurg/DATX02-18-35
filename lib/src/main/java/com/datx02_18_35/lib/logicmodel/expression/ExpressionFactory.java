package com.datx02_18_35.lib.logicmodel.expression;


import java.util.Collection;
import java.util.Set;

import jdk.internal.util.xml.impl.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by robin on 2018-02-07.
 */

public class ExpressionFactory {

    private static final ExpressionFactory singleton = new ExpressionFactory();

    private ExpressionFactory() {

    }

    public ExpressionFactory getSingleton() {
        return singleton;
    }

    public enum OperatorType {
        CONJUNCTION,
        DISJUNCTION,
        IMPLICATION,
    }

    public enum RuleType {
        IMPLICATIOM_ELIMINATION,

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
        throw new NotImplementedException();
    }


}
