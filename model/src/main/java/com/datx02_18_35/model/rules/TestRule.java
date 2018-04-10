package com.datx02_18_35.model.rules;

import com.datx02_18_35.model.expression.Absurdity;
import com.datx02_18_35.model.expression.Conjunction;
import com.datx02_18_35.model.expression.Disjunction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Implication;
import com.datx02_18_35.model.game.Session;

import java.util.Iterator;
import java.util.List;

/**
 * Created by robin on 2018-03-14.
 */

public final class TestRule {
    private TestRule() {}

    public static void assertRuleIsLegal(Session session, Rule rule) throws IllegalRuleException {
        // Shorthand variables
        final List<Expression> exprs = rule.expressions;
        final RuleType type = rule.type;

        // Check non-empty
        if (exprs.isEmpty()) {
            throw new IllegalRuleException(rule, "No expressions in rule!");
        }

        // Check expressions in scope
        switch (type) {
            case ABSURDITY_ELIMINATION:
                if (!session.isExpressionInScope(exprs.get(0))) {
                throw createIllegalRuleExceptionRuleNotInScope(rule);
            }
            break;
            case DISJUNCTION_INTRODUCTION:
                if (!(session.isExpressionInScope(exprs.get(0)) || session.isExpressionInScope(exprs.get(1)))) {
                    throw createIllegalRuleExceptionRuleNotInScope(rule);
                }
            break;
            case LAW_OF_EXCLUDED_MIDDLE:
                // Ignore
            break;
            default:
                if (!session.isExpressionInScope(exprs)) {
                    throw createIllegalRuleExceptionRuleNotInScope(rule);
                }
        }

        // Check correct number of expressions
        switch (type) {
            case LAW_OF_EXCLUDED_MIDDLE:
            case CONJUNCTION_ELIMINATION:
                if (rule.expressions.size() != 1) {
                    throw new IllegalRuleException(rule,
                            "Wrong number of expression in rule! " +
                                    "Expected: 1, Found: " + exprs.size());
                }
            break;
            case ABSURDITY_ELIMINATION:
            case IMPLICATION_ELIMINATION:
            case CONJUNCTION_INTRODUCTION:
            case DISJUNCTION_INTRODUCTION:
            case IMPLICATION_INTRODUCTION:
                if (rule.expressions.size() != 2) {
                    throw new IllegalRuleException(rule,
                            "Wrong number of expression in rule! " +
                                    "Expected: 2, Found: " + exprs.size());
                }
            break;
            case DISJUNCTION_ELIMINATION:
                if(rule.expressions.size()!=3){
                    throw new IllegalRuleException(rule,
                            "Wrong number of expression in rule!" +
                                    "Expected: 3, Found "+exprs.size());
                }
        }

        // Check correct types
        switch (type) {
            case LAW_OF_EXCLUDED_MIDDLE:
            case IMPLICATION_INTRODUCTION:
            case DISJUNCTION_INTRODUCTION:
            case CONJUNCTION_INTRODUCTION:
                // Ignore
                break;
            case CONJUNCTION_ELIMINATION:
                if (false == exprs.get(0) instanceof Conjunction) {
                    throw createIllegalRuleExceptionWrongTypes(rule);
                }
                break;
            case IMPLICATION_ELIMINATION:
                if (false == exprs.get(0) instanceof Implication) {
                    throw createIllegalRuleExceptionWrongTypes(rule);
                }
                break;
            case DISJUNCTION_ELIMINATION:
                if (   false == exprs.get(0) instanceof Disjunction
                        || false == exprs.get(1) instanceof Implication
                        || false == exprs.get(1) instanceof Implication) {
                    throw createIllegalRuleExceptionWrongTypes(rule);
                }
                break;
            case ABSURDITY_ELIMINATION:
                if (false == exprs.get(0) instanceof Absurdity) {
                    throw createIllegalRuleExceptionWrongTypes(rule);
                }
                break;
        }

    }

    private static IllegalRuleException createIllegalRuleExceptionRuleNotInScope(Rule rule) {
        return new IllegalRuleException(rule, "One of more expressions does not belong to the current scope!");
    }

    /*
    Assumes rule has already been checked for correct number of expressions
     */
    private static IllegalRuleException createIllegalRuleExceptionWrongTypes(Rule rule) {
        StringBuilder sb = new StringBuilder();
        sb.append("One or more expression is of the wrong type!");
        Iterator<Expression> iter = rule.expressions.iterator();
        if (iter.hasNext()) {
            sb.append(" [");
            sb.append(iter.next().getClass().getSimpleName());
            while (iter.hasNext()) {
                sb.append(",");
                sb.append(iter.next().getClass().getSimpleName());
            }
            sb.append("]");
        }
        return new IllegalRuleException(rule, sb.toString());
    }

}
