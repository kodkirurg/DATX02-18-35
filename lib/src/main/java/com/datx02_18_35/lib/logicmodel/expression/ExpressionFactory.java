package com.datx02_18_35.lib.logicmodel.expression;

import java.util.HashMap;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by robin on 2018-02-07.
 */

public class ExpressionFactory {

    private Map<String, Expression> expressions;

    private static final ExpressionFactory singleton = new ExpressionFactory();
    private ExpressionFactory() {
        expressions = new HashMap<>();
    }

    public ExpressionFactory getSingleton() {
        return singleton;
    }

    public enum Type {
        PROPOSITION,
        CONJUNCTION,
        DISJUNCTION,
    }

    public Expression create(
            Type type, String id, Expression... args) {
        assert id != null;
        switch (type) {
            case PROPOSITION: {
                assert args.length == 0;
                return createProposition(id);
            }
            case CONJUNCTION: {
                assert  args.length == 2;
                return createConjunction(id, args[0], args[1]);
            }
            case DISJUNCTION: {
                assert  args.length == 2;
                return createDisjunction(id, args[0], args[1]);
            }
            default: {
                throw new IllegalArgumentException("Unknown type");
            }
        }
    }

    private Expression createProposition(
            String id) {
        throw new NotImplementedException();
    }

    private Expression createConjunction(
            String id, Expression operand1, Expression operand2) {
        throw new NotImplementedException();
    }

    private Expression createDisjunction(
            String id, Expression operand1, Expression operand2) {
        throw new NotImplementedException();
    }
}
